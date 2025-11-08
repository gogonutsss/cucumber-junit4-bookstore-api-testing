package com.bookstore.api.dto.author;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Response POJO for Author API endpoints
 */
public class AuthorResponse {

    @JsonProperty("id")
    private int id;

    @JsonProperty("idBook")
    private int idBook;

    @JsonProperty("firstName")
    private String firstName;

    @JsonProperty("lastName")
    private String lastName;

    // Default constructor
    public AuthorResponse() {
    }

    // Constructor with all fields
    public AuthorResponse(int id, int idBook, String firstName, String lastName) {
        this.id = id;
        this.idBook = idBook;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    // Getters only (as requested)
    public int getId() {
        return id;
    }

    public int getIdBook() {
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