package org.openhds.integration;

import static org.junit.Assert.assertNotNull;
import java.util.Calendar;

import com.github.springtestdbunit.DbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseOperation;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import org.hibernate.SessionFactory;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.openhds.controller.service.CurrentUser;
import org.openhds.dao.service.GenericDao;
import org.openhds.domain.model.FieldWorker;
import org.openhds.domain.model.InMigration;
import org.openhds.domain.model.Individual;
import org.openhds.domain.model.Visit;
import org.openhds.domain.service.SitePropertiesService;
import org.openhds.domain.util.CalendarUtil;
import org.openhds.integration.util.JsfServiceMock;
import org.openhds.web.crud.impl.InMigrationCrudImpl;
import org.openhds.web.service.JsfService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.support.DirtiesContextTestExecutionListener;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;
import org.springframework.transaction.annotation.Transactional;

@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
@ContextConfiguration("/testContext.xml")
@TestExecutionListeners({ DependencyInjectionTestExecutionListener.class,
		DirtiesContextTestExecutionListener.class, TransactionalTestExecutionListener.class,
		DbUnitTestExecutionListener.class })
@DatabaseSetup(value = "/formResourceTestDb.xml", type = DatabaseOperation.REFRESH)
public class InMigrationTest {
	
	 @Autowired
	 @Qualifier("inMigrationCrud")
	 InMigrationCrudImpl inmigrationCrud;
	 	 	 
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
	 Visit visit;
	 JsfServiceMock jsfServiceMock;
	 
	 @Before
	 public void setUp() {
		 
		 jsfServiceMock = (JsfServiceMock)jsfService;
		 currentUser.setProxyUser("admin", "test", new String[] {"VIEW_ENTITY", "CREATE_ENTITY"});
		 
		 fieldWorker = genericDao.findByProperty(FieldWorker.class, "extId", "FWEK1D");
		 individual = genericDao.findByProperty(Individual.class, "extId", "individual1", false);
		 visit = genericDao.findByProperty(Visit.class, "extId", "TestVisit");
	 }
	 
	 @Test
	 public void testInMigrationCreate() {
		 assertNotNull(fieldWorker);
		 assertNotNull(individual);
		 assertNotNull(visit);
		 
		 InMigration inmig = new InMigration();
		 inmig.setIndividual(individual);
		 inmig.setCollectedBy(fieldWorker);
		 inmig.setRecordedDate(calendarUtil.getCalendar(Calendar.JANUARY, 4, 1990));
		 inmig.setMigTypeInternal();
		 inmig.setOrigin("place");
		 inmig.setReason("reason");
		 inmig.setUnknownIndividual(false);
		 inmig.setVisit(visit);
		 
		 inmigrationCrud.setItem(inmig);
		 inmigrationCrud.create();
	     
	     InMigration savedInMig = genericDao.findByProperty(InMigration.class, "individual", individual, false);
		 //assertNotNull(savedInMig);
	 }
}

