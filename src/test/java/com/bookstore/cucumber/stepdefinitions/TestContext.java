package com.bookstore.cucumber.stepdefinitions;

import com.bookstore.api.dto.book.BookRequest;
import com.bookstore.api.dto.book.BookResponse;
import com.bookstore.api.dto.author.AuthorRequest;
import com.bookstore.api.dto.author.AuthorResponse;
import io.restassured.response.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Test context to share data between step definitions
 */
public class TestContext {

	private static final Logger logger = LoggerFactory.getLogger(TestContext.class);

	// Current working objects
	private BookRequest currentBookRequest;
	private AuthorRequest currentAuthorRequest;
	// Response objects
	private Response lastBooksResponse;
	private Response lastAuthorsResponse;
	private Response lastResponse;
	// Test data
	private Map<Integer, List<Integer>> invalidBookToAuthors = new HashMap<>();
	private Set<Integer> addedBooks = new HashSet<>();
	private Set<Integer> addedAuthors = new HashSet<>();

	public TestContext() {
		logger.debug("TestContext initialized");
	}

	public Response getLastBooksResponse() {
		return lastBooksResponse;
	}

	public void setLastBooksResponse(Response lastBooksResponse) {
		this.lastBooksResponse = lastBooksResponse;
		this.lastResponse = lastBooksResponse;
		logger.debug("Last Books response set with status: {}", lastBooksResponse.getStatusCode());
	}

	public Response getLastAuthorsResponse() {
		return lastAuthorsResponse;
	}

	public void setLastAuthorsResponse(Response lastAuthorsResponse) {
		this.lastAuthorsResponse = lastAuthorsResponse;
		this.lastResponse = lastAuthorsResponse;
		logger.debug("Last Authors response set with status: {}", lastAuthorsResponse.getStatusCode());
	}

	public Response getLastResponse() {
		return lastResponse;
	}
	//Helpers
	public List<BookResponse> getBookList() {
		return Arrays.asList(this.lastBooksResponse.as(BookResponse[].class));
	}

	public List<AuthorResponse> getAuthorList() {
		return Arrays.asList(this.lastAuthorsResponse.as(AuthorResponse[].class));
	}
	
	public Map<Integer, List<Integer>> getInvalidBookToAuthors() {
		return invalidBookToAuthors;
	}

	public void setInvalidBookToAuthors(Map<Integer, List<Integer>> invalidBookToAuthors) {
		this.invalidBookToAuthors = invalidBookToAuthors;
	}

	public Set<Integer> getAddedBooks() {
		return addedBooks;
	}

	public Set<Integer> getAddedAuthors() {
		return addedAuthors;
	}

	// Current book request getters and setters
	public BookRequest getCurrentBookRequest() {
		return currentBookRequest;
	}

	public void setCurrentBookRequest(BookRequest currentBookRequest) {
		this.currentBookRequest = currentBookRequest;
		logger.debug("Current book request set: {}", currentBookRequest.getTitle());
	}

	// Current book response getters and setters
	public BookResponse getCurrentBookResponse() {
		return extractAs(this.lastBooksResponse, BookResponse.class);
	}

	// Current author request getters and setters
	public AuthorRequest getCurrentAuthorRequest() {
		return currentAuthorRequest;
	}

	public void setCurrentAuthorRequest(AuthorRequest currentAuthorRequest) {
		this.currentAuthorRequest = currentAuthorRequest;
		logger.debug("Current author request set: {} {}", currentAuthorRequest.getFirstName(),
				currentAuthorRequest.getLastName());
	}

	// Current author response getters and setters
	public AuthorResponse getCurrentAuthorResponse() {
		return extractAs(this.lastAuthorsResponse, AuthorResponse.class);
	}

	// Utility methods
	public void reset() {
		logger.debug("Resetting test context");
		lastBooksResponse = null;
		lastAuthorsResponse = null;
		lastResponse = null;
		invalidBookToAuthors = null;
		currentBookRequest = null;
		currentAuthorRequest = null;

	}

	/**
	 * Extract response body as specified type
	 * 
	 * @param response The response object
	 * @param clazz    The class type to deserialize to
	 * @param <T>      The type parameter
	 * @return Deserialized object
	 */
	public <T> T extractAs(Response response, Class<T> clazz) {
		return response.as(clazz);
	}
}