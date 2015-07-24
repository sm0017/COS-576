package org.openhds.controller.service.impl;

import java.util.Calendar;
import java.util.Iterator;
import java.util.List;

import org.openhds.controller.exception.ConstraintViolations;
import org.openhds.controller.service.*;
import org.openhds.dao.service.GenericDao;
import org.openhds.domain.annotations.Authorized;
import org.openhds.domain.model.FieldWorker;
import org.openhds.domain.model.Individual;
import org.openhds.domain.model.Membership;
import org.openhds.domain.model.SocialGroup;
import org.openhds.domain.service.SitePropertiesService;
import org.openhds.domain.util.CalendarUtil;
import org.springframework.transaction.annotation.Transactional;

public class MembershipServiceImpl extends EntityServiceRefactoredImpl implements MembershipService {

    private IndividualService individualService;
    private GenericDao genericDao;
    private SitePropertiesService siteProperties;

    public MembershipServiceImpl(GenericDao genericDao, IndividualService individualService, SitePropertiesService siteProperties,
                                 EntityValidationService entityValidationService, CalendarUtil calendarUtil, CurrentUser currentUser) {
        super(genericDao, currentUser, calendarUtil, siteProperties, entityValidationService);
        this.genericDao = genericDao;
        this.individualService = individualService;
        this.siteProperties = siteProperties;
    }

    public Membership evaluateMembershipBeforeCreate(Membership entityItem) throws ConstraintViolations {
        if (!checkDuplicateMembership(entityItem.getIndividual(), entityItem.getSocialGroup()))
            throw new ConstraintViolations(
                    "A Membership for the specified Social Group already exists.");
        if (individualService.getLatestEvent(entityItem.getIndividual()).equals("Death"))
            throw new ConstraintViolations(
                    "A Membership cannot be created for an Individual who has a Death event.");
        return entityItem;
    }

    public Membership evaluateMembershipBeforeUpdate(Membership membership) throws ConstraintViolations {

        //TODO: check constraints before persisting the Membership update
        if (null == membership) {
            throw new ConstraintViolations("Cannot update a null Membership");
        }
        return membership;

    }

    public Membership checkMembership(Membership persistedItem, Membership entityItem)
            throws ConstraintViolations {

        if (!compareDeathInMembership(persistedItem, entityItem))
            throw new ConstraintViolations(
                    "A Membership cannot be saved because an attempt was made to modify the end event type on an Individual who has a Death event.");
        if (!checkEndEventTypeForMembershipOnEdit(persistedItem, entityItem))
            throw new ConstraintViolations(
                    "A Membership cannot be saved because the end event type of Death cannot apply to Individuals who do not have a Death event.");

        return entityItem;
    }

    /**
     * Checks if a duplicate Membership already exists
     */
    public boolean checkDuplicateMembership(Individual indiv, SocialGroup group) {

        List<Membership> list = genericDao
                .findListByProperty(Membership.class, "individual", indiv);

        Iterator<Membership> itr = list.iterator();

        while (itr.hasNext()) {
            Membership item = itr.next();
            if (item.getSocialGroup().getExtId().equals(group.getExtId()) && !item.isDeleted())
                return false;
        }
        return true;
    }

    /**
     * Compares the persisted and (soon to be persisted) Membership items. If
     * the persisted item does not have an end type of Death and the entityItem
     * does, the edit can only continue if one of the Individuals has a Death
     * Event.
     */
    public boolean checkEndEventTypeForMembershipOnEdit(Membership persistedItem,
            Membership entityItem) {

        if (entityItem.getEndType().equals(siteProperties.getDeathCode())
                && !individualService.getLatestEvent(persistedItem.getIndividual()).equals("Death")
                && !individualService.getLatestEvent(persistedItem.getIndividual()).equals("Death"))
            return false;

        return true;
    }

    /**
     * Compares the persisted and (soon to be persisted) Membership items. If
     * the persisted item and entity item has a mismatch of an end event type
     * and the persisted item has a end type of death, the edit cannot be saved.
     */
    public boolean compareDeathInMembership(Membership persistedItem, Membership entityItem) {

        if (individualService.getLatestEvent(persistedItem.getIndividual()).equals("Death"))
            if (persistedItem.getEndType().equals(siteProperties.getDeathCode())
                    && !entityItem.getEndType().equals(siteProperties.getDeathCode()))
                return false;

        return true;
    }

    /**
     * Helper method for creating a membership. NOTE: This is only being used by
     * the pregnancy outcome web service method
     * 
     * @param startDate
     * @param individual
     * @param sg
     * @param fw
     * @param relationToGroupHead
     * @return
     */
    public Membership createMembershipForPregnancyOutcome(Calendar startDate,
            Individual individual, SocialGroup sg, FieldWorker fw, String relationToGroupHead) {
        Membership membership = new Membership();
        membership.setStartDate(startDate);
        membership.setIndividual(individual);
        membership.setSocialGroup(sg);
        membership.setCollectedBy(fw);
        membership.setbIsToA(relationToGroupHead);
        membership.setStartType(siteProperties.getBirthCode());
        membership.setEndType(siteProperties.getNotApplicableCode());

        return membership;
    }

    public void validateGeneralMembership(Membership membership) throws ConstraintViolations {
        if (individualIsHeadOfSocialGroup(membership.getIndividual(), membership.getSocialGroup())) {
            throw new ConstraintViolations(
                    "A Membership cannot be created for an Individual who is the head of the Social Group");
        }
    }

    public List<Membership> getAllMemberships(Individual individual) {
        List<Membership> items = genericDao.findListByProperty(Membership.class, "individual",
                individual, true);
        return items;
    }

    public List<Membership> getAllMemberships() {
        List<Membership> items = genericDao.findAll(Membership.class, true);
        return items;
    }

    /**
     * Determine whether the Individual is the head of the Social Group.
     * 
     * @param individual
     * @param socialGroup
     * 
     * @return true is the Individual is the head of the Social Group
     */
    private boolean individualIsHeadOfSocialGroup(Individual individual, SocialGroup socialGroup) {
        return socialGroup.getGroupHead().getExtId().equals(individual.getExtId());
    }

    public Membership updateMembership(Membership membership) throws ConstraintViolations {
        evaluateMembershipBeforeUpdate(membership);
        save(membership);
        return membership;

    }

    @Override
    @Transactional
    public void createMembership(Membership membership) throws ConstraintViolations {
        // assume a default start type of in migration
        if (membership.getStartType() == null) {
            membership.setStartType(siteProperties.getInmigrationCode());
        }

        if (membership.getEndType() == null) {
            membership.setEndType(siteProperties.getNotApplicableCode());
        }

        evaluateMembershipBeforeCreate(membership);
        create(membership);

    }
    
    @Override
    @Authorized("VIEW_ENTITY")
    public List<Membership> getAllMembershipsInRange(int start, int size) {
        return genericDao.findPaged(Membership.class, "individual", start, size);
    }
    
    @Override
    @Authorized("VIEW_ENTITY")
    public long getTotalMembershipCount() {
        return genericDao.getTotalCount(Membership.class);
    }
}
