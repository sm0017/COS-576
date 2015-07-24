package org.openhds.webservice.resources;

import java.io.Serializable;
import java.sql.SQLException;

import org.openhds.controller.exception.ConstraintViolations;
import org.openhds.controller.service.DeathService;
import org.openhds.domain.model.Death;
import org.openhds.domain.model.FieldWorker;
import org.openhds.domain.model.Individual;
import org.openhds.domain.model.Visit;
import org.openhds.webservice.FieldBuilder;
import org.openhds.webservice.WebServiceCallException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/deaths")
public class DeathResource {

    private DeathService deathService;
    private FieldBuilder fieldBuilder;

    @Autowired
    public DeathResource(DeathService deathService, FieldBuilder fieldBuilder) {
        this.deathService = deathService;
        this.fieldBuilder = fieldBuilder;
    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<? extends Serializable> insert(@RequestBody Death death) {
        ConstraintViolations cv = new ConstraintViolations();
        death.setCollectedBy(fieldBuilder.referenceField(death.getCollectedBy(), cv));
        death.setIndividual(fieldBuilder.referenceField(death.getIndividual(), cv,
                "Individual external id referenced in death event is invalid"));
        death.setVisitDeath(fieldBuilder.referenceField(death.getVisitDeath(), cv));

        if (cv.hasViolations()) {
            return badRequest(cv);
        }

        try {
            deathService.createDeath(death);
        } catch (ConstraintViolations e) {
            return badRequest(cv);
        } catch (SQLException e) {
            return badRequest(new ConstraintViolations("There was a database problem saving the death event"));
        }

        return new ResponseEntity<Death>(copy(death), HttpStatus.CREATED);
    }

    private ResponseEntity<? extends Serializable> badRequest(ConstraintViolations cv) {
        return new ResponseEntity<WebServiceCallException>(new WebServiceCallException(cv), HttpStatus.BAD_REQUEST);
    }

    private Death copy(Death death) {
        Death copy = new Death();
        copy.setCollectedBy(copyFieldWorker(death.getCollectedBy()));
        copy.setDeathCause(death.getDeathCause());
        copy.setDeathDate(death.getDeathDate());
        copy.setDeathPlace(death.getDeathPlace());
        copy.setIndividual(copyIndividual(death.getIndividual()));
        copy.setVisitDeath(copyVisit(death.getVisitDeath()));

        return copy;
    }

    private FieldWorker copyFieldWorker(FieldWorker collectedBy) {
        FieldWorker fw = new FieldWorker();
        fw.setExtId(collectedBy.getExtId());
        return fw;
    }

    private Individual copyIndividual(Individual individual) {
        Individual copy = new Individual();
        copy.setExtId(individual.getExtId());
        return copy;
    }

    private Visit copyVisit(Visit visitDeath) {
        Visit copy = new Visit();
        copy.setExtId(visitDeath.getExtId());
        return copy;
    }

}
