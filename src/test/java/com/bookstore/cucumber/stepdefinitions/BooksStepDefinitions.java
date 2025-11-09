package com.bookstore.cucumber.stepdefinitions;

import com.bookstore.api.dto.book.BookRequest;
import com.bookstore.api.dto.book.BookResponse;
import com.bookstore.api.service.BookService;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.restassured.response.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Random;

import static org.junit.Assert.*;

/**
 * Step definitions for Books service
 */
public class BooksStepDefinitions {

	private static final Logger logger = LoggerFactory.getLogger(BooksStepDefinitions.class);

	private final TestContext testContext;
	private final BookService bookService;

	public BooksStepDefinitions(TestContext testContext) {
		this.testContext = testContext;
		this.bookService = new BookService();
	}

	// Given steps
	@Given("there are books available in the store")
	public void thereAreBooksAvailableInTheStore() {
		logger.info("Getting all books are available in the store");

		Response response = bookService.getAllBooks();
		testContext.setLastBooksResponse(response);
		List<BookResponse> books = testContext.getBookList();

		assertNotNull("Books list should not be null", books);
		assertFalse("Books list should not be empty", books.isEmpty());

		logger.info("Found {} books in the store", books.size());
	}

	@Given("I prepare the details for a new book to be added")
	public void iHaveValidBookDetails(DataTable dataTable) {
		logger.info("Preparing book details for a book to be created");

		Map<String, String> bookData = dataTable.asMap(String.class, String.class);

		BookRequest bookRequest = BookRequest.builder().id(bookData.get("id"))
				.title(bookData.get("title")).description(bookData.get("description"))
				.pageCount(Integer.parseInt(bookData.get("pageCount"))).excerpt(bookData.get("excerpt"))
				.publishDate(LocalDateTime.now().toString()).build();

		testContext.setCurrentBookRequest(bookRequest);
		logger.info("Book details prepared: {}", bookRequest);
	}

	@When("I request to add this new book")
	public void iRequestToAddThisNewBook() {
		logger.info("Going to request to add a new book");
		BookRequest bookRequest = testContext.getCurrentBookRequest();
		assertNotNull("Book request should be set", bookRequest);

		Response response = bookService.createBook(bookRequest);
		String bookId = bookRequest.getId();
		testContext.setLastBooksResponse(response);
		testContext.getAddedBooks().add(bookId);
		logger.info("Requested to add a new book with id {}", bookId);
	}

	@Given("I have created a book")
	public void iHaveCreatedABook() {
		logger.info("Creating a book for test setup");
		// given this service, setting id while creating is a bit odd, anyways, I set
		// just a hardcoded value for the timebeing
		String bookId = "1";
		BookRequest bookRequest = BookRequest.builder().id(bookId).title("Test Book for Cucumber")
				.description("A book created for testing purposes").pageCount(200)
				.excerpt("This is a test book excerpt").publishDate(LocalDateTime.now().toString()).build();
		testContext.setCurrentBookRequest(bookRequest);
		Response response = bookService.createBook(bookRequest);
		bookService.validateSuccessResponse(response);
		testContext.setLastBooksResponse(response);
		testContext.getAddedBooks().add(bookId);
		logger.info("Created book with id {}", bookId);
	}

	@Given("I prepare the details for the book to be updated")
	public void iHaveUpdatedBookDetails(DataTable dataTable) {
		logger.info("Preparing book details for a book to be updated");

		BookRequest lastCreationRequest = testContext.getCurrentBookRequest();

		Map<String, String> bookData = dataTable.asMap(String.class, String.class);

		BookRequest updateRequest = BookRequest.builder().id(lastCreationRequest.getId()).title(bookData.get("title"))
				.description(bookData.get("description")).pageCount(Integer.parseInt(bookData.get("pageCount")))
				.excerpt(bookData.get("excerpt")).publishDate(LocalDateTime.now().toString()).build();

		testContext.setCurrentBookRequest(updateRequest);
		logger.info("Updated book details prepared: {}", updateRequest);
	}

	@Given("I prepare the details for a new book with title {string} and page count {int}")
	public void iHaveBookDetailsWithTitleAndPageCount(String title, int pageCount) {
		logger.info("Setting up book details with title: {} and page count: {}", title, pageCount);

		BookRequest bookRequest = BookRequest.builder().id("1").title(title).description("Description for " + title)
				.pageCount(pageCount).excerpt("Excerpt for " + title).publishDate(LocalDateTime.now().toString())
				.build();

		testContext.setCurrentBookRequest(bookRequest);
	}

	// When steps
	@When("I request all books")
	public void iRequestAllBooks() {
		logger.info("Requesting all books");
		Response response = bookService.getAllBooks();
		testContext.setLastBooksResponse(response);
	}

	@When("I request a book by its ID")
	public void iRequestABookByItsID() {
		logger.info("Requesting a book by ID");

		List<BookResponse> books = testContext.getBookList();

		// ensure we will get all books, in case someone is using this step
		// without having first called the step "there are books available in the store"
		if (books == null || books.isEmpty()) {
			Response response = bookService.getAllBooks();
			testContext.setLastBooksResponse(response);
			books = testContext.getBookList();
		}

		// atm, any 0<=id<=books.size works...
		Random random = new Random();
		int randomBook = random.nextInt(books.size() + 1);
		String bookId = books.get(randomBook).getId();

		Response response = bookService.getBookById(bookId);
		testContext.setLastBooksResponse(response);
		logger.info("Requested book with ID {}", bookId);
	}

	@When("I request to update the book")
	public void iRequestToUpdateTheBook() {
		logger.info("Requesting to updating a book");

		BookRequest updateRequest = testContext.getCurrentBookRequest();
		String bookId = updateRequest.getId();

		Response response = bookService.updateBook(bookId, updateRequest);
		testContext.setLastBooksResponse(response);
	}

	@When("I request to delete the book")
	public void iRequestToDeleteTheBook() {
		logger.info("Requesting to delete a book");

		String bookId = testContext.getCurrentBookRequest().getId();
		Response response = bookService.deleteBook(bookId);
		testContext.setLastBooksResponse(response);
		logger.info("Deleted book with ID: {}", bookId);
	}

	@When("I request to {word} a book by non-existent ID {string}")
	public void iRequestABookWithNonExistentID(String method, String bookId) {
		logger.info("Requesting to {} book with non-existent ID: {}", method, bookId);
		Response response = null;
		try {
			switch (method) {
				case "get": {
					response = bookService.getBookById(bookId);
					break;
				}
				case "update": {
					BookRequest updateRequest = BookRequest.builder().id(bookId).title("title")
							.description("description")
							.pageCount(100).excerpt("excerpt").publishDate(LocalDateTime.now().toString()).build();
					response = bookService.updateBook(bookId, updateRequest);
					break;
				}
				case "delete": {
					response = bookService.deleteBook(bookId);
					break;
				}
				default:
					throw new IllegalArgumentException("Unexpected value: " + method);
			}
		} catch (Exception e) {
			logger.info("Expected exception for non-existent book: {}", e.getMessage());
		}
		testContext.setLastBooksResponse(response);

	}
}