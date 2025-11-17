package utils;

import config.ConfigurationManager;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.specification.RequestSpecification;

/**
 * Utility class for creating standardized RestAssured RequestSpecification objects.
 * Provides a centralized way to configure common request parameters such as base URI,
 * content type, and headers that are used across multiple API test requests.
 */
public class RequestSpecBuilderUtil {

    /**
     * Creates and returns a RequestSpecification with common API configuration.
     * Builds a reusable request specification that includes base URI from configuration,
     * JSON content type, and required API key header for authentication.
     *
     * @return RequestSpecification configured with base URI, content type, and API key header
     */
    public static RequestSpecification getRequestSpec(){
        return new RequestSpecBuilder()
                .setBaseUri(ConfigurationManager.getProperty("base.url"))
                .setContentType("application/json")
                .addHeader("x-api-key", "reqres-free-v1")
                .build();


    }
}
