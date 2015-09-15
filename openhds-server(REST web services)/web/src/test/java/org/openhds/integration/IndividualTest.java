package org.openhds.integration;

import static org.junit.Assert.*;

import java.util.Calendar;

import org.hibernate.SessionFactory;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.openhds.controller.service.CurrentUser;
import org.openhds.dao.service.GenericDao;
import org.openhds.domain.model.FieldWorker;
import org.openhds.domain.model.Individual;
import org.openhds.domain.model.Location;
import org.openhds.domain.model.Membership;
import org.openhds.domain.service.SitePropertiesService;
import org.openhds.domain.util.CalendarUtil;
import org.openhds.web.crud.impl.IndividualCrudImpl;
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
public class IndividualTest extends AbstractTransactionalJUnit4SpringContextTests {
	
	 @Autowired
	 @Qualifier("individualCrud")
	 IndividualCrudImpl individualCrud;
	 	 	 
	 @Autowired
	 SessionFactory sessionFactory;
	 
	 @Autowired
	 GenericDao genericDao;
	 
	 @Autowired
	 SitePropertiesService siteProperties;
	 
	 @Autowired
	 CalendarUtil calendarUtil;
	 
	 @Autowired
	 @Qualifier("currentUser")
	 CurrentUser currentUser;
	 
	 @Autowired
	 JsfService jsfService;
	 
     @Test
     //setting proxy user and creating individual to test webservices
	 public void testIndividualCreate() {
		 
		 currentUser.setProxyUser("admin", "test", new String[] {"VIEW_ENTITY", "CREATE_ENTITY"});
		
		 FieldWorker fieldWorker = genericDao.findByProperty(FieldWorker.class, "extId", "FWEK1D");
		 Individual unknown = genericDao.findByProperty(Individual.class, "extId", "UNK");
		 Individual individual = new Individual();
		 individual.setCollectedBy(fieldWorker);
		 individual.setDeleted(false);
		 individual.setDob(calendarUtil.getCalendar(10, 10, 2010));
		 individual.setDobAspect("1");
		 individual.setExtId("individualte");
		 individual.setFather(unknown);
		 individual.setMother(unknown);
		 individual.setFirstName("Ram");
		 individual.setLastName("Menon");
		 individual.setGender("M");
	     individual.setCollectedBy(fieldWorker);
	     individualCrud.setItem(individual);
	     individualCrud.create();
	     
	     Individual savedIndividual = genericDao.findByProperty(Individual.class, "extId", individual.getExtId(), false);
	     assertNotNull(savedIndividual);	     
	    
	 }
}
