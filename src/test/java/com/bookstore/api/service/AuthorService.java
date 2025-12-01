package com.bookstore.api.service;

import com.bookstore.api.client.BookstoreApiClient;
import com.bookstore.api.config.ApiConfig;
import com.bookstore.api.dto.author.AuthorRequest;
import io.restassured.response.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Service class for Author API operations
 */
public class AuthorService extends BookstoreApiClient {

	private static final Logger logger = LoggerFactory.getLogger(AuthorService.class);

	/**
	 * Get all authors
	 * 
	 * @return Response object
	 */
	public Response getAllAuthors() {
		logger.info("Fetching all authors");
		return doGet(ApiConfig.getAuthorsEndpoint());
	}

	/**
	 * Get a specific author by ID
	 * 
	 * @param authorId The author ID
	 * @return Response object
	 */
	public Response getAuthorById(String authorId) {
		logger.info("Fetching author with ID {}", authorId);
		return doGet(ApiConfig.getAuthorsEndpoint(), authorId);
	}

	/**
	 * Create a new author
	 * 
	 * @param authorRequest The author request object
	 * @return Response object
	 */
	public Response createAuthor(AuthorRequest authorRequest) {
		logger.info("Creating new author with ID {}, firstname '{}' and lastname '{}'", authorRequest.getId(),
				authorRequest.getFirstName(), authorRequest.getLastName());
		return doPost(ApiConfig.getAuthorsEndpoint(), authorRequest);
	}

	/**
	 * Update an existing author
	 * 
	 * @param authorId      The author ID to update
	 * @param authorRequest The updated author request object
	 * @return Response object
	 */
	public Response updateAuthor(String authorId, AuthorRequest authorRequest) {
		logger.info("Updating author with ID {}", authorId);
		return doPut(ApiConfig.getAuthorsEndpoint(), authorId, authorRequest);
	}

	/**
	 * Delete an author by ID
	 * 
	 * @param authorId The author ID to delete
	 * @return Response object
	 */
	public Response deleteAuthor(String authorId) {
		logger.info("Deleting author with ID {}", authorId);
		return doDelete(ApiConfig.getAuthorsEndpoint(), authorId);
	}

	/**
	 * Check if an author exists by ID
	 * 
	 * @param authorId The author ID to check
	 * @return true if author exists, false otherwise
	 */
	public boolean authorExists(String authorId) {
		try {
			Response response = doGet(ApiConfig.getAuthorsEndpoint(), authorId);
			return response.getStatusCode() == 200;
		} catch (Exception e) {
			logger.debug("Author with ID {} does not exist", authorId);
			return false;
		}
	}

}