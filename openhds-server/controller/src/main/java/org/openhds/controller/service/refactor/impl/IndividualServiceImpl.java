package org.openhds.controller.service.refactor.impl;

import org.openhds.controller.exception.ConstraintViolations;
import org.openhds.controller.service.refactor.IndividualService;
import org.openhds.controller.service.refactor.ResidencyService;
import org.openhds.controller.service.refactor.crudhelpers.EntityCrudHelper;
import org.openhds.dao.service.GenericDao;
import org.openhds.domain.model.Death;
import org.openhds.domain.model.Individual;
import org.openhds.domain.model.Location;
import org.openhds.domain.service.SitePropertiesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.List;

@Service
public class IndividualServiceImpl implements IndividualService {

    private static final String UNKNOWN_EXTID = "UNK";

    @Autowired
    private SitePropertiesService sitePropertiesService;

    @Autowired
    @Qualifier("IndividualCrudHelper")
    private EntityCrudHelper<Individual> individualCrudHelper;

    @Autowired
    private ResidencyService residencyService;

    @Autowired
    private GenericDao genericDao;

    @Override
    public String generateChildExtId(Individual mother) {
        Location residencyLocation = mother.getCurrentResidency().getLocation();
        int existingResidents = residencyService.getResidenciesByLocation(residencyLocation).size();
        return sequencedExtId(mother.getExtId(), existingResidents + 1);
    }

    private String sequencedExtId(String extId, int sequenceNumber) {
        String newExtId = extId.substring(0, extId.length() - 3);
        return newExtId + String.format("%03d", sequenceNumber);
    }

    @Override
    public int getExistingExtIdCount(String extId) {

        List<Individual> existingIndividuals = genericDao.findListByProperty(Individual.class, "extId", extId);
        return (null == existingIndividuals) ?  0 : existingIndividuals.size();

    }

    @Override
    public List<Individual> getAll() {
        return individualCrudHelper.getAll();
    }

    @Override
    public Individual getByExtId(String id) {
        return individualCrudHelper.getByExtId(id);
    }

    @Override
    public Individual getByUuid(String id) {
        return individualCrudHelper.getByUuid(id);
    }

    @Override
    public boolean isEligibleForCreation(Individual individual, ConstraintViolations cv) {

        if (null == individual) {
            ConstraintViolations.addViolationIfNotNull(cv, "Null individual.");
            return false;
        }

        boolean nullExtId = (null == individual.getExtId());

        if (nullExtId) {
            ConstraintViolations.addViolationIfNotNull(cv, "The individual has a null ExtId.");
        }

        return !nullExtId;
    }

    @Override
    public void delete(Individual individual) throws IllegalArgumentException {
        individualCrudHelper.delete(individual);
    }

    @Override
    public void create(Individual individual) throws ConstraintViolations {
        individualCrudHelper.create(individual);
    }

    @Override
    public void save(Individual individual) throws ConstraintViolations {
        individualCrudHelper.save(individual);
    }

    @Override
    public boolean isDeceased(Individual individual) {
        //TODO: refactor the "getLatestEvent" logic in the old IndividualService
        return (null != genericDao.findByProperty(Death.class, "individual", individual, true));

    }

    @Override
    public Individual getUnknownIndividual() throws ConstraintViolations {
        Individual individual = getByExtId(UNKNOWN_EXTID);
        return individual;
    }

    protected static Calendar getDateInPast() {
        Calendar inPast = Calendar.getInstance();
        inPast.set(1900, Calendar.JANUARY, 1);
        return inPast;
    }

}
