package com.bookstore.api.service;

import com.bookstore.api.client.BookstoreApiClient;
import com.bookstore.api.config.ApiConfig;
import com.bookstore.api.dto.book.BookRequest;
import io.restassured.response.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Service class for Book API operations
 */
public class BookService extends BookstoreApiClient {

	private static final Logger logger = LoggerFactory.getLogger(BookService.class);

	/**
	 * Get all books
	 * 
	 * @return Response object
	 */
	public Response getAllBooks() {
		logger.info("Fetching all books");
		return doGet(ApiConfig.getBooksEndpoint());
	}

	/**
	 * Get a specific book by ID
	 * 
	 * @param bookId The book ID
	 * @return Response object
	 */
	public Response getBookById(int bookId) {
		logger.info("Fetching book with ID {}", bookId);
		return doGet(ApiConfig.getBooksEndpoint(), bookId);
	}

	/**
	 * Create a new book
	 * 
	 * @param bookRequest The book request object
	 * @return Response object
	 */
	public Response createBook(BookRequest bookRequest) {
		logger.info("Creating new book with ID {} and title '{}'", bookRequest.getId(), bookRequest.getTitle());

		return doPost(ApiConfig.getBooksEndpoint(), bookRequest);
	}

	/**
	 * Update an existing book
	 * 
	 * @param bookId      The book ID to update
	 * @param bookRequest The updated book request object
	 * @return Response object
	 */
	public Response updateBook(int bookId, BookRequest bookRequest) {
		logger.info("Updating book with ID {}", bookId);
		return doPut(ApiConfig.getBooksEndpoint(), bookId, bookRequest);
	}

	/**
	 * Delete a book by ID
	 * 
	 * @param bookId The book ID to delete
	 * @return Response object
	 */
	public Response deleteBook(int bookId) {
		logger.info("Deleting book with ID {}", bookId);
		return doDelete(ApiConfig.getBooksEndpoint(), bookId);
	}

	/**
	 * Check if a book exists by ID
	 * 
	 * @param bookId The book ID to check
	 * @return true if book exists, false otherwise
	 */
	public boolean bookExists(int bookId) {
		try {
			Response response = doGet(ApiConfig.getBooksEndpoint(), bookId);
			return response.getStatusCode() == 200;
		} catch (Exception e) {
			logger.debug("Book with ID {} does not exist", bookId);
			return false;
		}
	}

}