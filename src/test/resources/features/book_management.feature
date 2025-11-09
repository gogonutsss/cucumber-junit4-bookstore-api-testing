@books @regression
Feature: BookStore - Books Management
  As a user of the Bookstore API
  I want to manage books
  So that I can perform CRUD operations on books

  @smoke
  Scenario: Get the list of all books
    When I request all books
    Then I should receive a successful response
    And the response should contain a list of books
    And each book in the response should have valid properties

  @smoke
  Scenario: Get a specific book by ID
   # For test isolation, we would have to create a book here
    Given there are books available in the store
    When I request a book by its ID
    Then I should receive a successful response
    And the book should have all required properties

  @smoke
  Scenario: Add a new book
    Given I prepare the details for a new book to be added
      | id          | 1                                        |
      | title       | Georgia's book TODO special char         |
      | description | A comprehensive guide to API testing     |
      | pageCount   | 350                                      |
      | excerpt     | This book covers all aspects of testing  |
    When I request to add this new book
    Then I should receive a successful response
    And the response should contain the book details
    And the book should have all required properties
    And the book should be stored in the system

  @smoke
  Scenario: Update an existing book
    Given I have created a book
    And I prepare the details for the book to be updated
      | title       | This is a title       |
      | description | This is a description |
      | pageCount   | 30                    |
      | excerpt     | Advanced aspects      |
    When I request to update the book
    Then I should receive a successful response
    And the response should contain the book details
    # This is an intentional failure - to reveal the tests do work
    And the book should be updated in the system

  @smoke
  Scenario: Delete a book
    Given I have created a book
    When I request to delete the book
    Then I should receive a successful response
    # This is an intentional failure - to reveal the tests do work
    And the book should be removed from the system

  @data-driven
  Scenario Outline: Create book with <testCaseDescription>
    Given I prepare the details for a new book with title "<title>" and page count <pageCount>
    When I request to add this new book
    Then I should receive a successful response
    And the response should contain the book title "<title>" and page count <pageCount>

    Examples:
      | testCaseDescription       | title                                            | pageCount |
      | negative page count       | Java Programming                                 | -2        |
      | zero page count           | Python Basics                                    | 0         |
      | Greek chars in title      | Ελληνικό τίτλο                                   | 100       |
      | special chars in title    | Special chars `~!@#$%^&*()_+{}[]:\";'<>,.?/      | 10000000  |