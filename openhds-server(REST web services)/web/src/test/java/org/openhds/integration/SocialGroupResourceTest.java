
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

// integration test to check SocialGruop Resource
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

public class SocialGroupResourceTest {

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



	private MockHttpSession getMockHttpSession(String username, String password) throws Exception
	{
		return (MockHttpSession)mockMvc.perform(post("/loginProcess")
				.param("j_username", username)
				.param("j_password", password)
				).andReturn()
				.getRequest()
				.getSession();

	}
	
	@Test
	public void testGetAllSocialGroups() throws Exception {
		mockMvc.perform(get("/socialgroups").session(session))
		.andExpect(status().isOk())
		.andExpect(content().mimeType(MediaType.APPLICATION_XML))
		.andExpect(xpath("/socialgroups").nodeCount(1))
		.andExpect(xpath("/socialgroups/socialgroup/extId").string("MBI1"));
		
	
	}

	@Test
	public void testGetSocialGroupByExtId() throws Exception {
		String socialGruopExtId = "MBI00000100";

		mockMvc.perform(get("/socialgroups/{extId}", socialGruopExtId).session(session))
		.andExpect(status().isOk())
		.andExpect(content().mimeType(MediaType.APPLICATION_XML))
		.andExpect(xpath("/socialgroups/socialgroup/extId").string("MBI00000100"))
		.andExpect(xpath("/socialgroups/socialgroup/groupType").string("FAM"));
	}
	
	
	
	
}














