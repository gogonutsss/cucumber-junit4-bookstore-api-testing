package com.bookstore.api.dto.author;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Request POJO for Author API endpoints (POST and PUT)
 */
public class AuthorRequest {

    @JsonProperty("id")
    private int id;

    @JsonProperty("idBook")
    private int idBook;

    @JsonProperty("firstName")
    private String firstName;

    @JsonProperty("lastName")
    private String lastName;

    // Default constructor
    public AuthorRequest() {
    }

    // Constructor using builder
    private AuthorRequest(Builder builder) {
        this.id = builder.id;
        this.idBook = builder.idBook;
        this.firstName = builder.firstName;
        this.lastName = builder.lastName;
    }

    // Getters and setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdBook() {
        return idBook;
    }

    public void setIdBook(int idBook) {
        this.idBook = idBook;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    // Static method to get builder
    public static Builder builder() {
        return new Builder();
    }

    // Builder class
    public static class Builder {
        private int id;
        private int idBook;
        private String firstName;
        private String lastName;

        public Builder id(int id) {
            this.id = id;
            return this;
        }

        public Builder idBook(int idBook) {
            this.idBook = idBook;
            return this;
        }

        public Builder firstName(String firstName) {
            this.firstName = firstName;
            return this;
        }

        public Builder lastName(String lastName) {
            this.lastName = lastName;
            return this;
        }

        public AuthorRequest build() {
            return new AuthorRequest(this);
        }
    }

    @Override
    public String toString() {
        return "AuthorRequest{" +
                "id=" + id +
                ", idBook=" + idBook +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                '}';
    }
}