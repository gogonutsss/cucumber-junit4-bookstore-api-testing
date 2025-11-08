@authors @regression
Feature: BookStore - Authors Management
  As a user of the Bookstore API
  I want to manage authors
  So that I can perform CRUD operations on authors

  @smoke
  Scenario: Get the list of all authors
    When I request all authors
    Then I should receive a successful response
    And the response should contain a list of authors
    And each author in the response should have valid properties

  @smoke
  Scenario: Get a specific author by ID
    # For test isolation, we would have to create a book here...
    Given there are authors available in the store
    When I request an author by their ID
    Then I should receive a successful response
    And the author should have all required properties

  @smoke
  Scenario: Add a new author
    Given I prepare the details for a new author to be added
      | id        | 1    |
      | firstName | John |
      | lastName  | Doe  |
      | idBook    | 1    |
    When I request to add this new author
    Then I should receive a successful response
    And the response should contain the author details
    And the author should be stored in the system

  @smoke
  Scenario: Update an existing author
    Given I have created an author
    And I prepare the details for the author to be updated
      | firstName | Jane  |
      | lastName  | Smith |
      | idBook    | 1     |
    When I request to update the author
    Then I should receive a successful response
    And the response should contain the author details
    # This is an intentional failure - to reveal the tests do work
    And the author should be updated in the system

  @smoke
  Scenario: Delete an author
    Given I have created an author
    When I request to delete the author
    Then I should receive a successful response
    # This is an intentional failure - to reveal the tests do work
    And the author should be removed from the system

  @error-handling
  Scenario Outline: Request to <method> non-existent author
    When I request to <method> an author by non-existent ID 0
    Then I should receive a not found response

    Examples:
      | method |
      | get    |
      # This is an intentional failure - to reveal the tests do work
      | update |
      | delete |

  @data-driven
  Scenario Outline: Create author with <testCaseDescription>
    Given I prepare the details for a new author with first name "<firstName>" and last name "<lastName>"
    When I request to add this new author
    Then I should receive a successful response
    And the response should contain the author first name "<firstName>" and last name "<lastName>"

    Examples:
      | firstName                                               | lastName                                                | testCaseDescription                      |
      # Boundary Testing - Length
      | A                                                       | B                                                       | Single character names                   |
      | VeryLongFirstNameWithMoreThanFiftyCharactersToTestLimit | VeryLongLastNameWithMoreThanFiftyCharactersToTestLimit  | Very long names                          |
      # Case Sensitivity
      | alice                                                   | johnson                                                 | All lowercase                            |
      | ALICE                                                   | JOHNSON                                                 | All uppercase                            |
      | aLiCe                                                   | jOhNsOn                                                 | Mixed case                               |
      # Numeric Values
      | 123                                                     | 456                                                     | Only numbers                             |
      | Alice123                                                | Johnson456                                              | Alphanumeric                             |
      # Multiple Words, Titles and Suffixes
      | Mary Jane                                               | Van Der Berg                                            | Multi word names with space              |
      | Dr. Alice                                               | Johnson Jr.                                             | With titles                              |
      # Unicode and International Names
      | José                                                    | García                                                  | Accented characters                      |
      | François                                                | Müller                                                  | Diacritics umlauts                       |
      | Søren                                                   | Łukasz                                                  | Nordic Polish characters                 |
      | Αλέξανδρος                                              | Παπαδόπουλος                                            | Greek characters                         |
      | 田中                                                    | 太郎                                                    | Japanese characters                      |
      | Владимир                                                | Путин                                                   | Cyrillic characters                      |
      | محمد                                                    | علي                                                     | Arabic characters                        |
      | 李                                                      | 明                                                      | Chinese characters                       |
      # Edge Cases - Special Symbols, Emoji
      | Jean-Pierre                                             | O'Connor                                                | Hyphens and apostrophes                  |
      | Alice@123                                               | Johnson#456                                             | Numbers and symbols                      |
      | `~!@#$%^&*()                                           | _+-={}[]                                                | Special symbols                          |
      | Alice<script>                                           | Johnson</script>                                        | HTML script tags XSS test                |
      | Alice'; DROP TABLE--                                    | Johnson                                                 | SQL injection attempt                    |
      | Alice😀                                                 | Johnson🎉                                               | Emoji characters                         |
      # Empty/Null Cases (may expect validation errors)
      |                                                         | Johnson                                                 | Empty first name                         |
      | Alice                                                   |                                                         | Empty last name                          |
      |                                                         |                                                         | Empty both first and last name           |