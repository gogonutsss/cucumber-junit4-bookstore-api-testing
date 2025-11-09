package com.bookstore.api.dto.author;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Response POJO for Author API endpoints
 */
public class AuthorResponse {

    @JsonProperty("id")
    private String id;

    @JsonProperty("idBook")
    private String idBook;

    @JsonProperty("firstName")
    private String firstName;

    @JsonProperty("lastName")
    private String lastName;

    // Default constructor
    public AuthorResponse() {
    }

    // Constructor with all fields
    public AuthorResponse(String id, String idBook, String firstName, String lastName) {
        this.id = id;
        this.idBook = idBook;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    // Getters only (as requested)
    public String getId() {
        return id;
    }

    public String getIdBook() {
        return idBook;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    @Override
    public String toString() {
        return "AuthorResponse{" +
                "id=" + id +
                ", idBook=" + idBook +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                '}';
    }
}