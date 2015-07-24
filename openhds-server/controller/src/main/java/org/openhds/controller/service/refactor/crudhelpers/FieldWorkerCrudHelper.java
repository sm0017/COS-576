package org.openhds.controller.service.refactor.crudhelpers;

import org.openhds.controller.exception.ConstraintViolations;
import org.openhds.controller.service.refactor.FieldWorkerService;
import org.openhds.domain.model.FieldWorker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by wolfe on 9/19/14.
 */

@Component("FieldWorkerCrudHelper")
public class FieldWorkerCrudHelper extends AbstractEntityCrudHelperImpl<FieldWorker> {


    @Autowired
    FieldWorkerService fieldWorkerService;


    @Override
    protected void preCreateSanityChecks(FieldWorker fieldWorker) throws ConstraintViolations {

    }

    @Override
    protected void cascadeReferences(FieldWorker fieldWorker) throws ConstraintViolations {

        fieldWorkerService.generateId(fieldWorker);
        fieldWorkerService.generatePasswordHash(fieldWorker);
        fieldWorkerService.generateIdPrefix(fieldWorker);


    }

    @Override
    protected void validateReferences(FieldWorker fieldWorker) throws ConstraintViolations {

        ConstraintViolations constraintViolations = new ConstraintViolations();
        if (!fieldWorkerService.isEligibleForCreation(fieldWorker, constraintViolations)) {
            throw (constraintViolations);
        }

    }

    @Override
    public List<FieldWorker> getAll() {
        return genericDao.findAll(FieldWorker.class, true);
    }




    @Override
    public FieldWorker getByExtId(String id) {
        return genericDao.findByProperty(FieldWorker.class,"extId",id);
    }

    @Override
    public FieldWorker getByUuid(String id) {
        return genericDao.findByProperty(FieldWorker.class,"uuid",id);
    }
}
