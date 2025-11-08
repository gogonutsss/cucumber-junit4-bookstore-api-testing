package com.bookstore.api.config;

/**
 * Configuration class for the Bookstore API client
 */
public final class ApiConfig {

	// Base URL for the Bookstore API
	public static final String BASE_URL = "https://fakerestapi.azurewebsites.net";

	// API version prefix
	public static final String API_VERSION = "/api/v1";

	// Endpoints
	public static final String BOOKS_ENDPOINT = "/Books";
	public static final String AUTHORS_ENDPOINT = "/Authors";

	// HTTP Headers
	public static final String ACCEPT_HEADER = "accept";
	public static final String CONTENT_TYPE_HEADER = "Content-Type";
	public static final String ACCEPT_VALUE = "text/plain; v=1.0";
	public static final String CONTENT_TYPE_JSON = "application/json; v=1.0";

	// Private constructor to prevent instantiation
	private ApiConfig() {
	}

	/**
	 * Get the full base URL with API version
	 * 
	 * @return Full API base URL
	 */
	public static String getApiBaseUrl() {
		return BASE_URL + API_VERSION;
	}

	/**
	 * Get the full Books endpoint URL
	 * 
	 * @return Full Books endpoint URL
	 */
	public static String getBooksEndpoint() {
		return getApiBaseUrl() + BOOKS_ENDPOINT;
	}

	/**
	 * Get the full Authors endpoint URL
	 * 
	 * @return Full Authors endpoint URL
	 */
	public static String getAuthorsEndpoint() {
		return getApiBaseUrl() + AUTHORS_ENDPOINT;
	}
}