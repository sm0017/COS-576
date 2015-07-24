package org.openhds.task;

import org.apache.commons.codec.digest.DigestUtils;
import org.openhds.domain.util.CalendarAdapter;
import org.openhds.task.service.AsyncTaskService;
import org.springframework.scheduling.annotation.Async;
import org.springframework.transaction.annotation.Transactional;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamWriter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

/**
 * Template for writing entities to an XML file
 *
 * @param <T>
 *            The type of entities to write to the file
 */
public abstract class XmlWriterTemplate<T> implements XmlWriterTask {
    private static final int PAGE_SIZE = 100;

    private CalendarAdapter calendarAdapter;
    private AsyncTaskService asyncTaskService;
    private String taskName;

    public XmlWriterTemplate(AsyncTaskService asyncTaskService, String taskName) {
        this.asyncTaskService = asyncTaskService;
        this.taskName = taskName;
        calendarAdapter = new CalendarAdapter();
    }

    @Async
    @Transactional
    public void writeXmlAsync(TaskContext taskContext) {
        writeXml(taskContext);
    }

    public void writeXml(TaskContext taskContext) {

        try {
            OutputStream outputStream = new FileOutputStream(taskContext.getDestinationFile());

            asyncTaskService.startTask(taskName);

            long itemsWritten = 0L;
            int totalCount = getTotalEntityCount(taskContext);
            int totalPages = (int) Math.ceil((double) totalCount / PAGE_SIZE);

            XMLStreamWriter xmlStreamWriter = XMLOutputFactory.newInstance().createXMLStreamWriter(outputStream);
            xmlStreamWriter.writeStartDocument();
            xmlStreamWriter.writeStartElement(getStartElementName());
            JAXBContext context = JAXBContext.newInstance(getBoundClass());
            Marshaller marshaller = context.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FRAGMENT, true);
            marshaller.setAdapter(calendarAdapter);

            for (int i = 0; i < totalPages; i++) {
                List<T> entities = getEntitiesInRange(taskContext, (i * PAGE_SIZE), PAGE_SIZE);
                for (T original : entities) {
                    T copy = makeCopyOf(original);
                    marshaller.marshal(copy, xmlStreamWriter);
                    itemsWritten += 1;
                }

                asyncTaskService.updateTaskProgress(taskName, itemsWritten);

                // Empty the Hibernate cache
                // Prevents excessive memory use for large data sets like locations or individuals
                // See: http://docs.jboss.org/hibernate/orm/4.0/devguide/en-US/html/ch04.html
                asyncTaskService.clearSession();
            }

            xmlStreamWriter.writeEndElement();
            xmlStreamWriter.close();
            outputStream.close();

            InputStream inputStream = new FileInputStream(taskContext.getDestinationFile());
            String md5 = DigestUtils.md5Hex(inputStream);
            inputStream.close();

            asyncTaskService.finishTask(taskName, itemsWritten, md5);

        } catch (Exception e) {
            asyncTaskService.finishTask(taskName, 0, e.getMessage());
        }
    }

    protected abstract T makeCopyOf(T original);

    protected abstract List<T> getEntitiesInRange(TaskContext taskContext, int i, int pageSize);

    protected abstract Class<?> getBoundClass();

    protected abstract String getStartElementName();

    protected abstract int getTotalEntityCount(TaskContext taskContext);

}
