package com.bookstore.cucumber.stepdefinitions;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Cucumber Hooks for setup and teardown operations
 */
public class Hooks {

	private static final Logger logger = LoggerFactory.getLogger(Hooks.class);
	private final TestContext testContext;

	public Hooks(TestContext testContext) {
		this.testContext = testContext;
	}

	@Before
	public void setUp(Scenario scenario) {
		logger.info("Starting scenario: {}", scenario.getName());
		logger.info("Scenario tags: {}", scenario.getSourceTagNames());

		// Reset test context for each scenario
		testContext.reset();

		logger.debug("Test context reset completed");
	}

	@Before("@books")
	public void setUpBookTests(Scenario scenario) {
		logger.info("Setting up for book-related tests: {}", scenario.getName());
		// Any book-specific setup can go here
	}

	@Before("@authors")
	public void setUpAuthorTests(Scenario scenario) {
		logger.info("Setting up for author-related tests: {}", scenario.getName());
		// Any author-specific setup can go here
	}

	@Before("@integration")
	public void setUpIntegrationTests(Scenario scenario) {
		logger.info("Setting up for integration tests: {}", scenario.getName());
		// Any integration-specific setup can go here
	}

	@After
	public void tearDown(Scenario scenario) {
		logger.info("Finishing scenario: {} - Status: {}", scenario.getName(), scenario.getStatus());

		if (scenario.isFailed()) {
			logger.error("Scenario failed: {}", scenario.getName());

			// Log additional debugging information
			if (testContext.getLastBooksResponse() != null) {
				logger.error("Last Books response status: {}", testContext.getLastBooksResponse().getStatusCode());
				logger.error("Last Books response body: {}", testContext.getLastBooksResponse().getBody().asString());
			}
			if (testContext.getLastAuthorsResponse() != null) {
				logger.error("Last Authors response status: {}", testContext.getLastAuthorsResponse().getStatusCode());
				logger.error("Last Authors response body: {}",
						testContext.getLastAuthorsResponse().getBody().asString());
			}
		} else {
			logger.info("Scenario passed: {}", scenario.getName());
		}

		// Clean up any created test data
		cleanUpTestData();
	}

	/**
	 * Clean up any test data that might have been created during the scenario
	 */
	private void cleanUpTestData() {
		try {
			// Clean up created book(s) if exists
			if (!testContext.getAddedBooks().isEmpty()) {
				// In a real scenario, we would iterate to clean up the created data
			}

			// Clean up created author(s) if exists
			if (!testContext.getAddedAuthors().isEmpty()) {
				// Similar cleanup logic for authors
			}

		} catch (Exception e) {
			logger.warn("Error during test data cleanup: {}", e.getMessage());
			// Don't fail the test due to cleanup issues
			// but handle exceptions gracefully as the item might already be deleted or not
			// even created
		}
	}
}