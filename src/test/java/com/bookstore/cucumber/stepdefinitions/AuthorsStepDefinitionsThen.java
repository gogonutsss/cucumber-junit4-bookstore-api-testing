package com.bookstore.cucumber.stepdefinitions;

import com.bookstore.api.dto.author.AuthorRequest;
import com.bookstore.api.dto.author.AuthorResponse;
import com.bookstore.api.service.AuthorService;
import io.cucumber.java.en.Then;
import io.restassured.response.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.List;

import static org.junit.Assert.*;

/**
 * 'Then' Step definitions for Authors service
 */
public class AuthorsStepDefinitionsThen {

	private static final Logger logger = LoggerFactory.getLogger(AuthorsStepDefinitionsThen.class);

	private final TestContext testContext;
	private final AuthorService authorService;

	public AuthorsStepDefinitionsThen(TestContext testContext) {
		this.testContext = testContext;
		this.authorService = new AuthorService();
	}

	@Then("the response should contain a list of authors")
	public void theResponseShouldContainAListOfAuthors() {
		logger.info("Validating response contains a list of authors");

		List<AuthorResponse> authors = testContext.getAuthorList();
		assertNotNull("Authors list should not be null", authors);
		assertFalse("Authors list should not be empty", authors.isEmpty());

		logger.info("Validation successful - Found {} authors", authors.size());
	}

	@Then("each author in the response should have valid properties")
	public void eachAuthorShouldHaveValidProperties() {
		logger.info("Validating each author has valid properties");

		List<AuthorResponse> authors = testContext.getAuthorList();
		assertNotNull("Authors list should not be null", authors);

		for (AuthorResponse author : authors) {
			assertTrue("Author ID should not be null", author.getId() >= 1);
			assertNotNull("Author first name should not be null", author.getFirstName());
			assertNotNull("Author last name should not be null", author.getLastName());
			assertTrue("Author book ID should not be null", author.getIdBook() >= 1);
		}

		logger.info("All {} authors have valid properties", authors.size());
	}

	@Then("the response should contain the author details")
	public void theResponseShouldContainTheAuthorDetails() {
		logger.info("Validating response contains author details");

		AuthorResponse author = testContext.getCurrentAuthorResponse();
		AuthorRequest originalRequest = testContext.getCurrentAuthorRequest();

		assertNotNull("Author should not be null", author);
		assertTrue("Author ID should be assigned", author.getId() >= 1);
		assertEquals("Author first name should match", originalRequest.getFirstName(), author.getFirstName());
		assertEquals("Author last name should match", originalRequest.getLastName(), author.getLastName());
		assertEquals("Author book ID should match", originalRequest.getIdBook(), author.getIdBook());

		logger.info("Author validation successful: {}", author.getFirstName());
	}

	@Then("the author should have all required properties")
	public void theAuthorShouldHaveAllRequiredProperties() {
		logger.info("Validating author has all required properties");

		AuthorResponse author = testContext.getCurrentAuthorResponse();
		assertNotNull("Author should not be null", author);
		assertTrue("Author ID should not be null", author.getId() >= 1);
		assertNotNull("Author first name should not be null", author.getFirstName());
		assertNotNull("Author last name should not be null", author.getLastName());
		assertTrue("Author book ID should not be null", author.getIdBook() >= 1);

		logger.info("Author has all required properties: for id {}", author.getId());
	}

	@Then("the author should be stored in the system")
	public void theAuthorShouldBeStoredInTheSystem() {
		logger.info("Validating author is stored in the system");

		int authorId = testContext.getCurrentAuthorRequest().getId();
		boolean exists = authorService.authorExists(authorId);
		assertTrue("Author should exist in the system", exists);
		// TODO more assertions but it does not make sense given this mock...
		logger.info("Author is stored in the system with ID: {}", authorId);
	}

	@Then("the author should be updated in the system")
	public void theAuthorShouldBeUpdatedInTheSystem() {
		logger.info("Validating author is updated in the system");

		AuthorRequest updateRequest = testContext.getCurrentAuthorRequest();
		int authorId = updateRequest.getId();

		Response response = authorService.getAuthorById(authorId);
		testContext.setLastAuthorsResponse(response);
		AuthorResponse currentAuthor = testContext.getCurrentAuthorResponse();
		//
		assertEquals("Author first name should be updated in system", updateRequest.getFirstName(),
				currentAuthor.getFirstName());

		logger.info("Author is updated in the system: {}", currentAuthor.getFirstName());
	}

	@Then("the author should be removed from the system")
	public void theAuthorShouldBeRemovedFromTheSystem() {
		logger.info("Validating author is removed from the system");

		int authorId = testContext.getCurrentAuthorRequest().getId();
		boolean exists = authorService.authorExists(authorId);
		assertFalse("Author with ID " + authorId + " should not exist in the system after deletion", exists);

		logger.info("Author with ID {} is removed from the system", authorId);
	}

	@Then("the response should contain the author first name {string} and last name {string}")
	public void theResponseShouldContainAuthorFirstNameAndLastName(String expectedFirstName, String expectedLastName) {
		logger.info("Validating author first name and last name in the response");

		AuthorResponse author = testContext.getCurrentAuthorResponse();
		assertEquals("Author first name should match", expectedFirstName, author.getFirstName());
		assertEquals("Author last name should match", expectedLastName, author.getLastName());

		logger.info("Author validation successful - First Name: {}, Last Name: {}", expectedFirstName,
				expectedLastName);
	}

}