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

// integration test to check MembershipResource
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
//location for context file
@ContextConfiguration(loader=WebContextLoader.class, locations={"/testContext.xml"})
@TestExecutionListeners({ DependencyInjectionTestExecutionListener.class,
        DirtiesContextTestExecutionListener.class,
        TransactionalTestExecutionListener.class,
        DbUnitTestExecutionListener.class })
@DatabaseSetup(value = "/membershipResourceDb.xml", type = DatabaseOperation.REFRESH)
public class MembershipResourceTest {

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private FilterChainProxy springSecurityFilterChain;

    @Autowired
    private GenericDao genericDao;

    private MockHttpSession session;

    private MockMvc mockMvc;

    @Before
    public void setUp() throws Exception {
        mockMvc = MockMvcBuilders.webApplicationContextSetup(webApplicationContext)
                .addFilter(springSecurityFilterChain)
                .build();
         //get mock http session
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
    public void testPostMemebrsipXml() throws Exception {
        final String MEMBERSHIP_POST_XML = "<membership>"
               	+ "<bIsToA>7</bIsToA>"
               	+ "<collectedBy>"
				+ "<extId>FWEK1D</extId>"
               	+"<uuid>FieldWorker1</uuid>"
				+ "</collectedBy>"
        		+ "<individual>"
        		+ "<extId>MBI000001004</extId>"
        		+"<uuid>Indiv 4</uuid>"
        		+ "</individual>"
                + "<socialGroup>"
                + "<extId>MBI00000100</extId>"
                +"<uuid>SocialGroup1</uuid>"
                + "</socialGroup>"
                +"<startDate>2005-10-10</startDate>"
                + "</membership>";

        mockMvc.perform(post("/memberships").session(session)
                .contentType(MediaType.APPLICATION_XML)
                .body(MEMBERSHIP_POST_XML.getBytes()))
                .andExpect(status().isCreated())
                .andExpect(content().mimeType(MediaType.APPLICATION_XML))
                .andExpect(xpath("/membership/collectedBy/uuid").string("FieldWorker1"))
				.andExpect(xpath("/membership/bIsToA").string("7"))
				.andExpect(xpath("/membership/individual/uuid").string("Indiv 4"))
	       		.andExpect(xpath("/membership/socialGroup/uuid").string("SocialGroup1"))
    	        .andExpect(xpath("/membership/startDate").string("2005-10-10"));
        
        
    }



	

  
    
    


   
}
