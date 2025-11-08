@integration @regression @books @authors
Feature: BookStore - Combined Book and Author checks
  As a user of the Bookstore API
  I want to perform combined operations on books and authors
  So that I can manage relationships between books and their authors
 
  Scenario: All books referenced by authors should exist in the system
    Given there are books available in the store
    And there are authors available in the store
    When I collect the list of books that are referenced by authors and do not exist as books
    Then the list of not existent referenced books should be empty