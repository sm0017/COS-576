package org.openhds.integration;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.openhds.dao.service.GenericDao;
import org.openhds.integration.util.WebContextLoader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.security.web.FilterChainProxy;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.support.DirtiesContextTestExecutionListener;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.test.web.server.MockMvc;
import org.springframework.test.web.server.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.server.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.server.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.server.result.MockMvcResultMatchers.xpath;
import static org.springframework.test.web.server.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.server.request.MockMvcRequestBuilders.post;

import com.github.springtestdbunit.DbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseOperation;
import com.github.springtestdbunit.annotation.DatabaseSetup;

// integration test to check BaselineResource
//@Smita

@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
//location for context file
@ContextConfiguration(loader=WebContextLoader.class, locations={"/testContext.xml"})
@TestExecutionListeners({ DependencyInjectionTestExecutionListener.class,
	DirtiesContextTestExecutionListener.class,
	TransactionalTestExecutionListener.class,
	DbUnitTestExecutionListener.class })
@DatabaseSetup(value = "/baselineResourceDb.xml", type = DatabaseOperation.REFRESH)

public class BaselineResourceTest {

	@Autowired
	private WebApplicationContext webApplicationContext;

	@Autowired
	private FilterChainProxy springSecurityFilterChain;

	private MockHttpSession session;

	private MockMvc mockMvc;
	
    private static final String A_DOB =  "1987-01-01T00:00:00-00:00";

	@Before
	public void setUp() throws Exception {
		mockMvc = MockMvcBuilders.webApplicationContextSetup(webApplicationContext)
				.addFilter(springSecurityFilterChain)
				.build();
		session = getMockHttpSession("admin", "test");
	}



	private MockHttpSession getMockHttpSession(String username, String password) throws Exception {
		return (MockHttpSession)mockMvc.perform(post("/loginProcess")
				.param("j_username", username)
				.param("j_password", password)
				).andReturn()
				.getRequest()
				.getSession();

	}

	@Test
	public void testIndividualXml() throws Exception {
		final String Individual_POST_XML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>"
				+"<individual>"
				+ "<firstName>Stephnie</firstName>"
				+ "<middleName>A</middleName>"
				+ "<lastName>Steven</lastName>"
				+ "<gender>F</gender>"
				+ "<collectedBy>"
				+ "<extId>FWEK1D</extId>"
				+ "<uuid>FieldWorker1</uuid>"
				+ "</collectedBy>"
				+ "<dob>A_DOB</dob>"
				+ "<dobAspect>1</dobAspect>"
				+ "<mother>"
				+ "<extId>UNK</extId>"
				+ "<uuid>Unknown Individual</uuid>"
				+ "</mother>"
				+ "<father>"
				+ "<extId>UNK</extId>"
				+ "<uuid>Unknown Individual</uuid>"
				+ "</father>"
				+ "</individual>";

		mockMvc.perform(post("/baseline").session(session)
				.contentType(MediaType.APPLICATION_XML)
				.body(Individual_POST_XML.getBytes()))
				.andExpect(status().isCreated())
				.andExpect(content().mimeType(MediaType.APPLICATION_XML))
				.andExpect(xpath("/individual/firstName").string("Stephnie"))
				.andExpect(xpath("/individual/lastName").string("Steven"));


	}

}













