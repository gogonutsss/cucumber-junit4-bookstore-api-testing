package com.bookstore.api.dto.book;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Book object request POJO for Book API endpoints (POST and PUT)
 */
public class BookRequest {

    @JsonProperty("id")
    private int id;

    @JsonProperty("title")
    private String title;

    @JsonProperty("description")
    private String description;

    @JsonProperty("pageCount")
    private int pageCount;

    @JsonProperty("excerpt")
    private String excerpt;

    @JsonProperty("publishDate")
    private String publishDate;

    // Default constructor
    public BookRequest() {
    }

    // Constructor using builder
    private BookRequest(Builder builder) {
        this.id = builder.id;
        this.title = builder.title;
        this.description = builder.description;
        this.pageCount = builder.pageCount;
        this.excerpt = builder.excerpt;
        this.publishDate = builder.publishDate;
    }

    // Getters and setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getPageCount() {
        return pageCount;
    }

    public void setPageCount(int pageCount) {
        this.pageCount = pageCount;
    }

    public String getExcerpt() {
        return excerpt;
    }

    public void setExcerpt(String excerpt) {
        this.excerpt = excerpt;
    }

    public String getPublishDate() {
        return publishDate;
    }

    public void setPublishDate(String publishDate) {
        this.publishDate = publishDate;
    }

    // Static method to get builder
    public static Builder builder() {
        return new Builder();
    }

    // Builder class
    public static class Builder {
        private int id;
        private String title;
        private String description;
        private int pageCount;
        private String excerpt;
        private String publishDate;

        public Builder id(int id) {
            this.id = id;
            return this;
        }

        public Builder title(String title) {
            this.title = title;
            return this;
        }

        public Builder description(String description) {
            this.description = description;
            return this;
        }

        public Builder pageCount(int pageCount) {
            this.pageCount = pageCount;
            return this;
        }

        public Builder excerpt(String excerpt) {
            this.excerpt = excerpt;
            return this;
        }

        public Builder publishDate(String publishDate) {
            this.publishDate = publishDate;
            return this;
        }

        public BookRequest build() {
            return new BookRequest(this);
        }
    }

    @Override
    public String toString() {
        return "BookRequest{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", pageCount=" + pageCount +
                ", excerpt='" + excerpt + '\'' +
                ", publishDate=" + publishDate +
                '}';
    }
}