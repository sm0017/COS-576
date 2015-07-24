package org.openhds.task;

import com.github.springtestdbunit.DbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseOperation;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import org.custommonkey.xmlunit.exceptions.XpathException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.openhds.controller.service.CurrentUser;
import org.openhds.controller.service.IndividualService;
import org.openhds.domain.model.PrivilegeConstants;
import org.openhds.task.service.AsyncTaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.support.DirtiesContextTestExecutionListener;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;
import org.springframework.transaction.annotation.Transactional;
import org.xml.sax.SAXException;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static org.custommonkey.xmlunit.XMLAssert.assertXpathEvaluatesTo;
import static org.custommonkey.xmlunit.XMLAssert.assertXpathExists;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static org.mockito.MockitoAnnotations.initMocks;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "/task-test-context.xml" })
@TestExecutionListeners({ DependencyInjectionTestExecutionListener.class,
        DirtiesContextTestExecutionListener.class,
        TransactionalTestExecutionListener.class,
        DbUnitTestExecutionListener.class })
@DatabaseSetup(value = "/taskTestDb.xml", type = DatabaseOperation.REFRESH)
@Transactional
public class IndividualXmlWriterTaskTest {

    @Autowired
    private AsyncTaskService asyncTaskService;

    @Autowired
    private IndividualService individualService;

    @Autowired
    private CurrentUser currentUser;

    @Before
    public void setUp() {
        initMocks(this);
        currentUser.setProxyUser("admin", "test", new String[] {
                PrivilegeConstants.CREATE_ENTITY,
                PrivilegeConstants.VIEW_ENTITY });
    }

    // Current implementation of the IndividualXmlWriterTask will only generate
    // XML for individuals that
    // have a residency. This test presumes that only the individual1 of the
    // test database will be written to xml
    @Test
    public void shouldWriteXml() {

        File fileToWrite = new File("individuals-test.xml");
        if (fileToWrite.exists()) {
            fileToWrite.delete();
        }

        try {

            IndividualXmlWriterTask task = new IndividualXmlWriterTask(asyncTaskService, individualService);
            TaskContext context = new TaskContext(fileToWrite);
            task.writeXml(context);

            assertTrue(fileToWrite.exists());
            String xmlWritten = new String(Files.readAllBytes(Paths.get(fileToWrite.getPath())));
            assertXpathExists("/individuals", xmlWritten);
            assertXpathExists("/individuals/individual", xmlWritten);
            assertXpathExists("/individuals/individual/memberships", xmlWritten);
            assertXpathExists("/individuals/individual/residencies", xmlWritten);

            assertXpathEvaluatesTo("Individual1", "/individuals/individual/uuid", xmlWritten);
            assertXpathEvaluatesTo("60", "/individuals/individual/age", xmlWritten);
            assertXpathEvaluatesTo("Individual", "/individuals/individual/firstName", xmlWritten);
            assertXpathEvaluatesTo("1", "/individuals/individual/lastName", xmlWritten);

        } catch (IOException e) {
            fail("IOException testing Individual XML: " + e.getMessage());
        } catch (SAXException e) {
            fail("SAXException testing Individual XML: " + e.getMessage());
        } catch (XpathException e) {
            fail("XpathException testing Individual XML: " + e.getMessage());
        }

        if (fileToWrite.exists()) {
            fileToWrite.delete();
        }
    }

}
