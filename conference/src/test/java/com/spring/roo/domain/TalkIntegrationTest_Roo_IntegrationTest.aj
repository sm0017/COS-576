// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package com.spring.roo.domain;

import com.spring.roo.domain.Talk;
import com.spring.roo.domain.TalkDataOnDemand;
import com.spring.roo.domain.TalkIntegrationTest;
import java.util.Iterator;
import java.util.List;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

privileged aspect TalkIntegrationTest_Roo_IntegrationTest {
    
    declare @type: TalkIntegrationTest: @RunWith(SpringJUnit4ClassRunner.class);
    
    declare @type: TalkIntegrationTest: @ContextConfiguration(locations = "classpath*:/META-INF/spring/applicationContext*.xml");
    
    declare @type: TalkIntegrationTest: @Transactional;
    
    @Autowired
    TalkDataOnDemand TalkIntegrationTest.dod;
    
    @Test
    public void TalkIntegrationTest.testCountTalks() {
        Assert.assertNotNull("Data on demand for 'Talk' failed to initialize correctly", dod.getRandomTalk());
        long count = Talk.countTalks();
        Assert.assertTrue("Counter for 'Talk' incorrectly reported there were no entries", count > 0);
    }
    
    @Test
    public void TalkIntegrationTest.testFindTalk() {
        Talk obj = dod.getRandomTalk();
        Assert.assertNotNull("Data on demand for 'Talk' failed to initialize correctly", obj);
        Long id = obj.getId();
        Assert.assertNotNull("Data on demand for 'Talk' failed to provide an identifier", id);
        obj = Talk.findTalk(id);
        Assert.assertNotNull("Find method for 'Talk' illegally returned null for id '" + id + "'", obj);
        Assert.assertEquals("Find method for 'Talk' returned the incorrect identifier", id, obj.getId());
    }
    
    @Test
    public void TalkIntegrationTest.testFindAllTalks() {
        Assert.assertNotNull("Data on demand for 'Talk' failed to initialize correctly", dod.getRandomTalk());
        long count = Talk.countTalks();
        Assert.assertTrue("Too expensive to perform a find all test for 'Talk', as there are " + count + " entries; set the findAllMaximum to exceed this value or set findAll=false on the integration test annotation to disable the test", count < 250);
        List<Talk> result = Talk.findAllTalks();
        Assert.assertNotNull("Find all method for 'Talk' illegally returned null", result);
        Assert.assertTrue("Find all method for 'Talk' failed to return any data", result.size() > 0);
    }
    
    @Test
    public void TalkIntegrationTest.testFindTalkEntries() {
        Assert.assertNotNull("Data on demand for 'Talk' failed to initialize correctly", dod.getRandomTalk());
        long count = Talk.countTalks();
        if (count > 20) count = 20;
        int firstResult = 0;
        int maxResults = (int) count;
        List<Talk> result = Talk.findTalkEntries(firstResult, maxResults);
        Assert.assertNotNull("Find entries method for 'Talk' illegally returned null", result);
        Assert.assertEquals("Find entries method for 'Talk' returned an incorrect number of entries", count, result.size());
    }
    
    @Test
    public void TalkIntegrationTest.testFlush() {
        Talk obj = dod.getRandomTalk();
        Assert.assertNotNull("Data on demand for 'Talk' failed to initialize correctly", obj);
        Long id = obj.getId();
        Assert.assertNotNull("Data on demand for 'Talk' failed to provide an identifier", id);
        obj = Talk.findTalk(id);
        Assert.assertNotNull("Find method for 'Talk' illegally returned null for id '" + id + "'", obj);
        boolean modified =  dod.modifyTalk(obj);
        Integer currentVersion = obj.getVersion();
        obj.flush();
        Assert.assertTrue("Version for 'Talk' failed to increment on flush directive", (currentVersion != null && obj.getVersion() > currentVersion) || !modified);
    }
    
    @Test
    public void TalkIntegrationTest.testMergeUpdate() {
        Talk obj = dod.getRandomTalk();
        Assert.assertNotNull("Data on demand for 'Talk' failed to initialize correctly", obj);
        Long id = obj.getId();
        Assert.assertNotNull("Data on demand for 'Talk' failed to provide an identifier", id);
        obj = Talk.findTalk(id);
        boolean modified =  dod.modifyTalk(obj);
        Integer currentVersion = obj.getVersion();
        Talk merged = obj.merge();
        obj.flush();
        Assert.assertEquals("Identifier of merged object not the same as identifier of original object", merged.getId(), id);
        Assert.assertTrue("Version for 'Talk' failed to increment on merge and flush directive", (currentVersion != null && obj.getVersion() > currentVersion) || !modified);
    }
    
    @Test
    public void TalkIntegrationTest.testPersist() {
        Assert.assertNotNull("Data on demand for 'Talk' failed to initialize correctly", dod.getRandomTalk());
        Talk obj = dod.getNewTransientTalk(Integer.MAX_VALUE);
        Assert.assertNotNull("Data on demand for 'Talk' failed to provide a new transient entity", obj);
        Assert.assertNull("Expected 'Talk' identifier to be null", obj.getId());
        try {
            obj.persist();
        } catch (final ConstraintViolationException e) {
            final StringBuilder msg = new StringBuilder();
            for (Iterator<ConstraintViolation<?>> iter = e.getConstraintViolations().iterator(); iter.hasNext();) {
                final ConstraintViolation<?> cv = iter.next();
                msg.append("[").append(cv.getRootBean().getClass().getName()).append(".").append(cv.getPropertyPath()).append(": ").append(cv.getMessage()).append(" (invalid value = ").append(cv.getInvalidValue()).append(")").append("]");
            }
            throw new IllegalStateException(msg.toString(), e);
        }
        obj.flush();
        Assert.assertNotNull("Expected 'Talk' identifier to no longer be null", obj.getId());
    }
    
    @Test
    public void TalkIntegrationTest.testRemove() {
        Talk obj = dod.getRandomTalk();
        Assert.assertNotNull("Data on demand for 'Talk' failed to initialize correctly", obj);
        Long id = obj.getId();
        Assert.assertNotNull("Data on demand for 'Talk' failed to provide an identifier", id);
        obj = Talk.findTalk(id);
        obj.remove();
        obj.flush();
        Assert.assertNull("Failed to remove 'Talk' with identifier '" + id + "'", Talk.findTalk(id));
    }
    
}