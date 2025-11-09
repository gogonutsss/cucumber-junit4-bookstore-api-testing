@books @regression
Feature: BookStore - Books Management - Edge Cases
  As a tester of the Bookstore API
  I want to test various ID formats for books
  So that I can validate proper error handling

  @error-handling
  Scenario Outline: Request to <method> non-existent book
    When I request to <method> a book by non-existent ID "0"
    Then I should receive a not found response

    Examples:
      | method |
      | get    |
      # This is an intentional failure - to reveal the tests do work
      | update |
      | delete |

  Scenario Outline: Request to create a book with id "<testId>"
    Given I prepare the details for a new book to be added
      | id          | <testId>                              |
      | title       | Test Book                             |
      | description | A test book with edge case ID         |
      | pageCount   | 100                                   |
      | excerpt     | This is a test excerpt                |
    When I request to add this new book
    # todo assertions
    Then I should receive a bad request response

    Examples:
      | testId | 
      | abc    |
      | null   | 
      |        | 

  Scenario Outline: Request to <method> a book with id "<invalidId>"
    When I request to <method> a book by non-existent ID "<invalidId>"
    # todo assertions
    Then I should receive a bad request response

    Examples:
      | method | invalidId |
      | get    | abc       | # String value instead of numeric
      | get    | null      | # Null value as string
      | update | xyz123    | # Mixed alphanumeric
      | delete |           | # Empty/missing ID