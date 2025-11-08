package com.bookstore.api.client;

import com.bookstore.api.config.ApiConfig;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static io.restassured.RestAssured.given;

/**
 * Abstract base API client for the Bookstore API providing common HTTP
 * operations
 * This class should be extended by service classes like BookService and
 * AuthorService
 */
public abstract class BookstoreApiClient {

    private static final Logger logger = LoggerFactory.getLogger(BookstoreApiClient.class);

    static {
        // Configure RestAssured defaults
        RestAssured.baseURI = ApiConfig.BASE_URL;
        // Only enable logging on failures, not always
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
    }

    /**
     * Create a base request specification with common headers and logging
     * 
     * @return RequestSpecification with default configuration
     */
    protected RequestSpecification createBaseRequest() {
        return given()
                .header(ApiConfig.ACCEPT_HEADER, ApiConfig.ACCEPT_VALUE)
                .contentType(ContentType.JSON)
                .log().ifValidationFails()
                .when();
    }

    /**
     * Perform GET request
     * 
     * @param endpoint The endpoint URL
     * @return Response object
     */
    public Response doGet(String endpoint) {
        logger.info("Performing GET request to: {}", endpoint);

        Response response = createBaseRequest()
                .get(endpoint);

        logResponse(response, "GET", endpoint);
        return response;
    }

    /**
     * Perform GET request with path parameter
     * 
     * @param endpoint The endpoint URL
     * @param id       The path parameter (usually ID)
     * @return Response object
     */
    public Response doGet(String endpoint, int id) {
        String fullEndpoint = endpoint + "/" + id;
        logger.info("Performing GET request to: {}", fullEndpoint);

        Response response = createBaseRequest()
                .get(fullEndpoint);

        logResponse(response, "GET", fullEndpoint);
        return response;
    }

    /**
     * Perform POST request
     * 
     * @param endpoint    The endpoint URL
     * @param requestBody The request body object
     * @return Response object
     */
    public Response doPost(String endpoint, Object requestBody) {
        logger.info("Performing POST request to: {}", endpoint);

        Response response = createBaseRequest()
                .body(requestBody)
                .post(endpoint);

        logResponse(response, "POST", endpoint);
        return response;
    }

    /**
     * Perform PUT request
     * 
     * @param endpoint    The endpoint URL
     * @param id          The path parameter (usually ID)
     * @param requestBody The request body object
     * @return Response object
     */
    public Response doPut(String endpoint, int id, Object requestBody) {
        String fullEndpoint = endpoint + "/" + id;
        logger.info("Performing PUT request to: {}", fullEndpoint);

        Response response = createBaseRequest()
                .body(requestBody)
                .put(fullEndpoint);

        logResponse(response, "PUT", fullEndpoint);
        return response;
    }

    /**
     * Perform DELETE request
     * 
     * @param endpoint The endpoint URL
     * @param id       The path parameter (usually ID)
     * @return Response object
     */
    public Response doDelete(String endpoint, int id) {
        String fullEndpoint = endpoint + "/" + id;
        logger.info("Performing DELETE request to: {}", fullEndpoint);

        Response response = createBaseRequest()
                .delete(fullEndpoint);

        logResponse(response, "DELETE", fullEndpoint);
        return response;
    }

    /**
     * Log response details
     * 
     * @param response The response object
     * @param method   HTTP method
     * @param endpoint The endpoint URL
     */
    private void logResponse(Response response, String method, String endpoint) {
        if (response.getStatusCode() >= 400) {
            logger.info("❌ {} {} → {} ({} ms) - {}", 
                    method, endpoint, response.getStatusCode(), response.getTime(),
                    response.getStatusLine());
            logger.debug("Error details: {}", response.getBody().asString());

        } else {
            logger.info("✅ {} {} → {} ({} ms)", 
                    method, endpoint, response.getStatusCode(), response.getTime());
            logger.debug("Response body: {}", response.getBody().asString());
        }
    }

    /**
     * Validate successful response (2xx status codes)
     * 
     * @param response The response to validate
     * @throws AssertionError if response is not successful
     */
    public void validateSuccessResponse(Response response) {
        if (response.getStatusCode() < 200 || response.getStatusCode() >= 300) {
            throw new AssertionError(
                    String.format("Expected successful response but got status: %d, body: %s",
                            response.getStatusCode(), response.getBody().asString()));
        }
    }

}