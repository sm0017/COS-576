package org.openhds.integration;

import static org.junit.Assert.*;

import java.util.Calendar;

import org.hibernate.SessionFactory;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.openhds.controller.service.CurrentUser;
import org.openhds.dao.service.GenericDao;
import org.openhds.domain.model.FieldWorker;
import org.openhds.domain.model.Individual;
import org.openhds.domain.model.Membership;
import org.openhds.domain.model.SocialGroup;
import org.openhds.domain.service.SitePropertiesService;
import org.openhds.domain.util.CalendarUtil;
import org.openhds.integration.util.JsfServiceMock;
import org.openhds.web.crud.impl.MembershipCrudImpl;
import org.openhds.web.service.JsfService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
@ContextConfiguration("/testContext.xml")
/** Integration test for Membership */
public class MembershipTest extends AbstractTransactionalJUnit4SpringContextTests {
	
	 @Autowired
	 @Qualifier("membershipCrud")
	 MembershipCrudImpl membershipCrud;
	 	 	 
	 @Autowired
	 SessionFactory sessionFactory;
	 
	 @Autowired
	 GenericDao genericDao;
	 
	 @Autowired
	 SitePropertiesService siteProperties;
	 
	 @Autowired
	 JsfService jsfService;
	 
	 @Autowired
	 CalendarUtil calendarUtil;
	 
	 @Autowired
	 @Qualifier("currentUser")
	 CurrentUser currentUser;
	 
	 FieldWorker fieldWorker;
	 Individual individual;
	 SocialGroup socialGroup;
	
	 
	 @Test
	 public void testMembershipCreate() {
		 
		 currentUser.setProxyUser("admin", "test", new String[] {"VIEW_ENTITY", "CREATE_ENTITY"});
		 
		 fieldWorker = genericDao.findByProperty(FieldWorker.class, "extId", "FWEK1D");
		 individual = genericDao.findByProperty(Individual.class, "extId", "BHAR1K");
		 socialGroup = genericDao.findByProperty(SocialGroup.class, "extId", "MBI1");
		 Membership membershiptest = new Membership();
		 membershiptest.setbIsToA("7");
		 membershiptest.setCollectedBy(fieldWorker);
		 membershiptest.setDeleted(false);
		 membershiptest.setIndividual(individual);
		 membershiptest.setSocialGroup(socialGroup);
		 membershiptest.setStartDate(calendarUtil.getCalendar(10, 10, 2013));
		 membershiptest.setStartType("ENU");
		 membershipCrud.setItem(membershiptest);
		 membershipCrud.create();
		 Membership savedMembership = genericDao.findByProperty(Membership.class, "individual", individual, false);
		 assertNotNull(savedMembership);
	 }
	
}
	
