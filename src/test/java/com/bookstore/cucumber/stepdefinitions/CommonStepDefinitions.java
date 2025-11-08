package com.bookstore.cucumber.stepdefinitions;

import io.cucumber.java.en.Then;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.junit.Assert.*;

/**
 * Shared step definitions for generic steps across services
 */
public class CommonStepDefinitions {
    
    private static final Logger logger = LoggerFactory.getLogger(CommonStepDefinitions.class);
    
    private final TestContext testContext;
    
    public CommonStepDefinitions(TestContext testContext) {
        this.testContext = testContext;
    }

    @Then("I should receive a successful response")
    public void iShouldReceiveASuccessfulResponse() {
        logger.info("Validating successful response");
        
        assertNotNull("Response should not be null", testContext.getLastResponse());
        
        int statusCode = testContext.getLastResponse().getStatusCode();
        assertTrue("Status code should be between 200-299, but was: " + statusCode, 
                statusCode >= 200 && statusCode < 300);
        
        logger.info("Response validation successful - Status: {}", statusCode);
    }
    
    @Then("I should receive a not found response")
    public void iShouldReceiveANotFoundResponse() {
        logger.info("Validating not found response");
        
        assertNotNull("Response should not be null", testContext.getLastResponse());
        
        int statusCode = testContext.getLastResponse().getStatusCode();
        assertEquals("Status code should be 404", 404, statusCode);
        
        logger.info("Not found response validation successful - Status: {}", statusCode);
    }
}