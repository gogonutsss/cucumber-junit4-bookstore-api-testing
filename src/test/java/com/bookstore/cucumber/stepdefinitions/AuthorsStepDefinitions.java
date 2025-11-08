package com.bookstore.cucumber.stepdefinitions;

import com.bookstore.api.dto.author.AuthorRequest;
import com.bookstore.api.dto.author.AuthorResponse;
import com.bookstore.api.service.AuthorService;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.restassured.response.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Random;

import static org.junit.Assert.*;

/**
 * Step definitions for Authors service
 */
public class AuthorsStepDefinitions {

	private static final Logger logger = LoggerFactory.getLogger(AuthorsStepDefinitions.class);

	private final TestContext testContext;
	private final AuthorService authorService;

	public AuthorsStepDefinitions(TestContext testContext) {
		this.testContext = testContext;
		this.authorService = new AuthorService();
	}

	// Given steps
	@Given("there are authors available in the store")
	public void thereAreAuthorsAvailableInTheStore() {
		logger.info("Getting all authors are available in the store");

		Response response = authorService.getAllAuthors();
		testContext.setLastAuthorsResponse(response);

		List<AuthorResponse> authors = Arrays.asList(response.as(AuthorResponse[].class));

		assertNotNull("Authors list should not be null", authors);
		assertFalse("Authors list should not be empty", authors.isEmpty());

		logger.info("Found {} authors in the store", authors.size());
	}

	@Given("I prepare the details for a new author to be added")
	public void iHaveValidAuthorDetails(DataTable dataTable) {
		logger.info("Preparing author details for an author to be created");

		Map<String, String> authorData = dataTable.asMap(String.class, String.class);

		AuthorRequest authorRequest = AuthorRequest.builder().id(Integer.valueOf(authorData.get("id")))
				.firstName(authorData.get("firstName")).lastName(authorData.get("lastName"))
				.idBook(Integer.parseInt(authorData.get("idBook"))).build();

		testContext.setCurrentAuthorRequest(authorRequest);
		logger.info("Author details prepared: {} {}", authorRequest.getFirstName(), authorRequest.getLastName());
	}

	@When("I request to add this new author")
	public void iRequestToAddThisNewAuthor() {
		logger.info("Going to request to add a new author");
		AuthorRequest authorRequest = testContext.getCurrentAuthorRequest();
		assertNotNull("Author request should be set", authorRequest);

		Response response = authorService.createAuthor(authorRequest);
		int authorId = authorRequest.getId();
		testContext.setLastAuthorsResponse(response);
		testContext.getAddedAuthors().add(authorId);
		logger.info("Requested to add a new author with id {}", authorId);
	}

	@Given("I have created an author")
	public void iHaveCreatedAnAuthor() {
		logger.info("Creating an author for test setup");
		// given this service, setting id while creating is a bit odd, anyways, I set
		// just a hardcoded value for the timebeing
		int authorId = 1;
		AuthorRequest authorRequest = AuthorRequest.builder().id(authorId).firstName("Test").lastName("Author")
				.idBook(1).build();
		testContext.setCurrentAuthorRequest(authorRequest);
		Response response = authorService.createAuthor(authorRequest);
		authorService.validateSuccessResponse(response);
		testContext.setLastAuthorsResponse(response);
		testContext.getAddedAuthors().add(authorId);
		logger.info("Created author with id {}", authorId);

	}

	@Given("I prepare the details for the author to be updated")
	public void iHaveUpdatedAuthorDetails(DataTable dataTable) {
		logger.info("Preparing author details for an author to be updated");

		AuthorRequest lastCreationRequest = testContext.getCurrentAuthorRequest();

		Map<String, String> authorData = dataTable.asMap(String.class, String.class);

		AuthorRequest updateRequest = AuthorRequest.builder().id(lastCreationRequest.getId())
				.firstName(authorData.get("firstName")).lastName(authorData.get("lastName"))
				.idBook(Integer.parseInt(authorData.get("idBook"))).build();

		testContext.setCurrentAuthorRequest(updateRequest);
		logger.info("Updated author details prepared: {} {}", updateRequest.getFirstName(),
				updateRequest.getLastName());
	}

	@Given("I prepare the details for a new author with first name {string} and last name {string}")
	public void iHaveAuthorDetailsWithFirstNameAndLastName(String firstName, String lastName) {
		logger.info("Setting up author details with first name: {} and last name: {}", firstName, lastName);

		AuthorRequest authorRequest = AuthorRequest.builder().id(1).firstName(firstName)
				.lastName(lastName).idBook(1) // Default
				.build();

		testContext.setCurrentAuthorRequest(authorRequest);
	}

	// When steps
	@When("I request all authors")
	public void iRequestAllAuthors() {
		logger.info("Requesting all authors");
		Response response = authorService.getAllAuthors();
		testContext.setLastAuthorsResponse(response);
	}

	@When("I request an author by their ID")
	public void iRequestAnAuthorByTheirID() {
		logger.info("Requesting an author by ID");

		List<AuthorResponse> authors = testContext.getAuthorList();

		// ensure we will get all authors, in case someone is using this step
		// without having first called the step "there are authors available in the
		// store"
		if (authors == null || authors.isEmpty()) {
			Response response = authorService.getAllAuthors();
			testContext.setLastAuthorsResponse(response);
			authors = testContext.getAuthorList();
		}

		// atm, any 0<=id<=authors.size works...
		Random random = new Random();
		int randomAuthor = random.nextInt(authors.size() + 1);
		int authorId = authors.get(randomAuthor).getId();

		Response response = authorService.getAuthorById(authorId);
		testContext.setLastAuthorsResponse(response);
		logger.info("Requested author with ID {}", authorId);
	}

	@When("I request to update the author")
	public void iRequestToUpdateTheAuthor() {
		logger.info("Requesting to update an author");

		AuthorRequest updateRequest = testContext.getCurrentAuthorRequest();
		int authorId = updateRequest.getId();

		Response response = authorService.updateAuthor(authorId, updateRequest);
		testContext.setLastAuthorsResponse(response);
	}

	@When("I request to delete the author")
	public void iRequestToDeleteTheAuthor() {
		logger.info("Requesting to delete an author");

		int authorId = testContext.getCurrentAuthorRequest().getId();
		Response response = authorService.deleteAuthor(authorId);
		testContext.setLastAuthorsResponse(response);
		logger.info("Deleted author with ID: {}", authorId);
	}

	@When("I request to {word} an author by non-existent ID {int}")
	public void iRequestAnAuthorWithNonExistentID(String method, int authorId) {
		logger.info("Requesting to {} author with non-existent ID: {}", method, authorId);
		Response response = null;
		try {
			switch (method) {
			case "get": {
				response = authorService.getAuthorById(authorId);
				break;
			}
			case "update": {
				AuthorRequest updateRequest = AuthorRequest.builder().id(authorId).firstName("firstName")
						.lastName("lastName").idBook(1).build();
				response = authorService.updateAuthor(authorId, updateRequest);
				break;
			}
			case "delete": {
				response = authorService.deleteAuthor(authorId);
				break;
			}
			default:
				throw new IllegalArgumentException("Unexpected value: " + method);
			}
		} catch (Exception e) {
			logger.info("Expected exception for non-existent author: {}", e.getMessage());
		}
		testContext.setLastAuthorsResponse(response);
	}

}