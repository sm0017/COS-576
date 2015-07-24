package org.openhds.webservice.resources.bioko;


import org.openhds.controller.exception.ConstraintViolations;
import org.openhds.controller.service.CurrentUser;
import org.openhds.controller.service.VisitService;
import org.openhds.controller.service.refactor.FieldWorkerService;
import org.openhds.controller.service.refactor.InMigrationService;
import org.openhds.controller.service.refactor.IndividualService;
import org.openhds.controller.service.refactor.LocationService;
import org.openhds.domain.model.*;
import org.openhds.domain.model.bioko.InMigrationForm;
import org.openhds.domain.service.SitePropertiesService;
import org.openhds.domain.util.CalendarAdapter;
import org.openhds.domain.util.CalendarUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import java.io.Serializable;
import java.io.StringWriter;
import java.util.Calendar;
import java.util.Date;
import java.util.UUID;


@Controller
@RequestMapping("/inMigrationForm")
public class InMigrationFormResource extends AbstractFormResource {

    @Autowired
    protected CurrentUser currentUser;

    @Autowired
    protected CalendarUtil calendarUtil;

    @Autowired
    private IndividualService individualService;

    @Autowired
    private FieldWorkerService fieldWorkerService;

    @Autowired
    private VisitService visitService;

    @Autowired
    private LocationService locationService;

    @Autowired
    private SitePropertiesService sitePropertiesService;

    @Autowired
    private InMigrationService inMigrationService;

    @Autowired
    private CalendarAdapter adapter;

    private JAXBContext context = null;
    private Marshaller marshaller = null;

    @RequestMapping(method = RequestMethod.POST, produces = "application/xml", consumes = "application/xml")
    @Transactional
    public ResponseEntity<? extends Serializable> processForm(@RequestBody InMigrationForm inMigrationForm) throws JAXBException {

        try {
            context = JAXBContext.newInstance(InMigrationForm.class);
            marshaller = context.createMarshaller();
            marshaller.setAdapter(adapter);
        } catch (JAXBException e) {
            throw new RuntimeException("Could not create JAXB context and marshaller for InMigrationFormResource");
        }

        InMigration inMigration = new InMigration();

        inMigration.setRecordedDate(inMigrationForm.getMigrationDate());
        inMigration.setReason(inMigrationForm.getMigrationReason());
        inMigration.setOrigin(inMigrationForm.getMigrationOrigin());

        //TODO: determine a consistent configuration plan between siteProperties and MigrationType enum
        //TODO: (question) why use an enum and not the siteproperties?
        if ("internal_inmigration".equals(inMigrationForm.getMigrationType())) {
            inMigration.setMigType(MigrationType.INTERNAL_INMIGRATION);
        } else {
            inMigration.setMigType(MigrationType.EXTERNAL_INMIGRATION);
        }

        FieldWorker fieldWorker = fieldWorkerService.getByUuid(inMigrationForm.getFieldWorkerUuid());
        if (null == fieldWorker) {
            ConstraintViolations cv = new ConstraintViolations();
            cv.addViolations(ConstraintViolations.INVALID_FIELD_WORKER_UUID + " : "+inMigrationForm.getFieldWorkerUuid());
            logError(cv, fieldWorker, createDTOPayload(inMigrationForm), InMigrationForm.class.getSimpleName(), ConstraintViolations.INVALID_FIELD_WORKER_UUID);
            return requestError(cv);
        }
        inMigration.setCollectedBy(fieldWorker);

        Visit visit = visitService.findVisitByUuid(inMigrationForm.getVisitUuid());
        if (null == visit) {
            ConstraintViolations cv = new ConstraintViolations();
            cv.addViolations(ConstraintViolations.INVALID_VISIT_UUID + " : "+inMigrationForm.getVisitUuid());
            logError(cv, fieldWorker, createDTOPayload(inMigrationForm), InMigrationForm.class.getSimpleName(), ConstraintViolations.INVALID_VISIT_UUID);
            return requestError(cv);
        }
        inMigration.setVisit(visit);

        Individual individual = individualService.getByUuid(inMigrationForm.getIndividualUuid());
        if (null == individual) {
            ConstraintViolations cv = new ConstraintViolations();
            cv.addViolations(ConstraintViolations.INVALID_INDIVIDUAL_UUID+" : "+inMigrationForm.getIndividualUuid());
            logError(cv, fieldWorker, createDTOPayload(inMigrationForm), InMigrationForm.class.getSimpleName(), ConstraintViolations.INVALID_INDIVIDUAL_UUID);
            return requestError(cv);
        }
        inMigration.setIndividual(individual);

        Location location = locationService.getByUuid(inMigrationForm.getLocationUuid());
        if (null == location) {
            ConstraintViolations cv = new ConstraintViolations();
            cv.addViolations(ConstraintViolations.INVALID_LOCATION_UUID+" : "+inMigrationForm.getLocationUuid());
            logError(cv, fieldWorker, createDTOPayload(inMigrationForm), InMigrationForm.class.getSimpleName(), ConstraintViolations.INVALID_LOCATION_UUID);
            return requestError(cv);
        }

        Residency newResidency = new Residency();

        //TODO: since the InMigration domain model contains a reference to a Residency instead of a Location,
        //we must assemble the Residency at this level to provide a fully-graphed InMigration to the service
        newResidency.setCollectedBy(fieldWorker);

        newResidency.setLocation(location);
        newResidency.setIndividual(individual);
        newResidency.setUuid(UUID.randomUUID().toString().replace("-", ""));
        newResidency.setStartDate(inMigration.getRecordedDate());
        newResidency.setStartType(sitePropertiesService.getInmigrationCode());
        newResidency.setEndType(sitePropertiesService.getNotApplicableCode());

        if (null != currentUser) {
            newResidency.setInsertBy(currentUser.getCurrentUser());
        }

        Calendar insertDate = calendarUtil.convertDateToCalendar(new Date());
        newResidency.setInsertDate(insertDate);

        newResidency.setStatus(sitePropertiesService.getDataStatusPendingCode());

        inMigration.setResidency(newResidency);

        try {
            inMigrationService.create(inMigration);
        } catch (ConstraintViolations cv) {
            logError(cv, fieldWorker, createDTOPayload(inMigrationForm), InMigrationForm.class.getSimpleName(),  ConstraintViolations.INVALID_IN_MIGRATION);
            return requestError(cv);
        }

        return new ResponseEntity<InMigrationForm>(inMigrationForm, HttpStatus.CREATED);

    }

    private String createDTOPayload(InMigrationForm inMigrationForm) throws JAXBException {
        StringWriter writer = new StringWriter();
        marshaller.marshal(inMigrationForm, writer);
        return writer.toString();
    }


}
