package org.openhds.webservice.resources;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.openhds.controller.exception.ConstraintViolations;
import org.openhds.controller.service.EntityService;
import org.openhds.controller.service.VisitService;
import org.openhds.domain.model.Visit;
import org.openhds.domain.model.Visit.Visits;
import org.openhds.domain.util.ShallowCopier;
import org.openhds.task.support.FileResolver;
import org.openhds.controller.util.CacheResponseWriter;
import org.openhds.webservice.FieldBuilder;
import org.openhds.webservice.WebServiceCallException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/visits")
public class VisitResource {
    private static final Logger logger = LoggerFactory.getLogger(VisitResource.class);

    private final VisitService visitService;
    private final FieldBuilder fieldBuilder;
    private final FileResolver fileResolver;

    @Autowired
    private CacheResponseWriter cacheResponseWriter;

    @Autowired
    public VisitResource(VisitService visitService, FieldBuilder fieldBuilder, EntityService entityService,
            FileResolver fileResolver) {
        this.visitService = visitService;
        this.fieldBuilder = fieldBuilder;
        this.fileResolver = fileResolver;
    }

    @RequestMapping(method = RequestMethod.GET)
    @ResponseBody
    public Visits getAllVisits() {
        List<Visit> allVisits = visitService.getAllVisits();
        List<Visit> copies = new ArrayList<Visit>(allVisits.size());

        for (Visit visit : allVisits) {
            Visit copy = ShallowCopier.makeShallowCopy(visit);

            copies.add(copy);
        }

        Visits visits = new Visit.Visits();
        visits.setVisits(copies);

        return visits;
    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<? extends Serializable> insert(@RequestBody Visit visit) {
        ConstraintViolations cv = new ConstraintViolations();
        visit.setVisitLocation(fieldBuilder.referenceField(visit.getVisitLocation(), cv));
        visit.setCollectedBy(fieldBuilder.referenceField(visit.getCollectedBy(), cv));

        if (cv.hasViolations()) {
            return new ResponseEntity<WebServiceCallException>(new WebServiceCallException(cv), HttpStatus.BAD_REQUEST);
        }

        try {
            visitService.createVisit(visit);
        } catch (ConstraintViolations e) {
            return new ResponseEntity<WebServiceCallException>(new WebServiceCallException(cv), HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<Visit>(ShallowCopier.makeShallowCopy(visit), HttpStatus.CREATED);
    }

    @RequestMapping(value = "/cached", method = RequestMethod.GET)
    public void getCachedVisits(HttpServletResponse response) {
        try {
            cacheResponseWriter.writeResponse(fileResolver.resolveVisitXmlFile(), response);
        } catch (IOException e) {
            logger.error("Problem writing visit xml file: " + e.getMessage());
        }
    }
}
