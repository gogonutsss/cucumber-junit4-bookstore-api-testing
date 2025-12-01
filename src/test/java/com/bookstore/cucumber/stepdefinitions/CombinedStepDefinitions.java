package com.bookstore.cucumber.stepdefinitions;

import com.bookstore.api.dto.book.BookResponse;
import com.bookstore.api.dto.author.AuthorResponse;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import static org.junit.Assert.*;

/**
 * Step definitions for combined Books and Authors service test logic
 */
public class CombinedStepDefinitions {

	private static final Logger logger = LoggerFactory.getLogger(CombinedStepDefinitions.class);

	private final TestContext testContext;

	public CombinedStepDefinitions(TestContext testContext) {
		this.testContext = testContext;
	}

	// When steps
	@When("I collect the list of books that are referenced by authors and do not exist as books")
	public void iCollectListOfBooksReferencedByAuthorsButNotExistentAsBooks() {

		logger.info("Collecting the list of books that are referenced by authors and do not exist as books");

		List<AuthorResponse> allAuthors = testContext.getAuthorList();
		List<BookResponse> allBooks = testContext.getBookList();

		Map<String, List<String>> bookIdToAuthorIds = allAuthors.stream().filter(author -> {
			try {
				return author.getIdBook() != null && !author.getIdBook().trim().isEmpty();
			} catch (Exception e) {
				return false;
			}
		}).collect(Collectors.groupingBy(AuthorResponse::getIdBook,
				Collectors.mapping(AuthorResponse::getId, Collectors.toList())));

		// I iterate through the list of unique books from authors to see which are
		// found to not exist inside the list of books
		Map<String, List<String>> invalidBookToAuthors = new HashMap<>();

		Set<String> listOfUniqueBooksIds = allBooks.stream().map(BookResponse::getId).filter(Objects::nonNull)
				.collect(Collectors.toSet());

		for (Map.Entry<String, List<String>> entry : bookIdToAuthorIds.entrySet()) {
			String bookId = entry.getKey();
			List<String> authorIds = entry.getValue();

			if (!listOfUniqueBooksIds.contains(bookId)) {
				invalidBookToAuthors.put(bookId, authorIds);
			}

		}
		testContext.setInvalidBookToAuthors(invalidBookToAuthors);
	}

	// Then steps
	@Then("the list of not existent referenced books should be empty")
	public void theListOfNotExistentReferencedBooksShouldBeEmpty() {
		logger.info("Validating that the list of not existent referenced books is empty");

		Map<String, List<String>> invalidBookToAuthors = testContext.getInvalidBookToAuthors();

		assertTrue("All books referenced by authors should exist in the system: "
				+ formatValidationErrors(invalidBookToAuthors), invalidBookToAuthors.isEmpty());

	}

	private String formatValidationErrors(Map<String, List<String>> invalidBookToAuthors) {
		StringBuilder message = new StringBuilder();
		message.append("\n❌ Data integrity validation failed:\n\n");

		invalidBookToAuthors.forEach((bookId, authorIds) -> {
			message.append(String.format(
					"📚 Book ID: %s (NOT FOUND)\n" + "   ↳ Linked to %d author(s) with ID(s): %s\n\n", bookId,
					authorIds.size(), authorIds.stream().collect(Collectors.joining(", "))));
		});

		message.append(String.format("Summary: %d missing book(s) affecting %d author(s)\n",
				invalidBookToAuthors.size(), invalidBookToAuthors.values().stream().mapToInt(List::size).sum()));

		return message.toString();
	}

}