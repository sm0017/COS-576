package org.openhds.controller.service.refactor.crudhelpers;

import org.openhds.controller.exception.ConstraintViolations;
import org.openhds.controller.idgeneration.LocationGenerator;
import org.openhds.controller.service.refactor.LocationService;
import org.openhds.domain.model.Location;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by wolfe on 9/19/14.
 */

@Component("LocationCrudHelper")
public class LocationCrudHelper extends AbstractEntityCrudHelperImpl<Location> {


    @Autowired
    LocationService locationService;

    @Autowired
    LocationGenerator locationGenerator;

    @Override
    protected void preCreateSanityChecks(Location location) throws ConstraintViolations {



    }

    @Override
    protected void cascadeReferences(Location location) throws ConstraintViolations {

        if(null == location.getExtId()){
            locationGenerator.generateId(location);
        }

    }

    @Override
    protected void validateReferences(Location location) throws ConstraintViolations {

        ConstraintViolations constraintViolations = new ConstraintViolations();
        if (!locationService.isEligibleForCreation(location, constraintViolations)) {
            throw (constraintViolations);
        }

    }

    @Override
    public List<Location> getAll() {
        return genericDao.findAll(Location.class, true);
    }

    @Override
    public Location getByExtId(String id) {
        return genericDao.findByProperty(Location.class,"extId",id);
    }

    @Override
    public Location getByUuid(String id) {
        return genericDao.findByProperty(Location.class,"uuid",id);
    }

}
