package tests;

import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import static org.testng.Assert.assertEquals;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import io.qameta.allure.Allure;
import io.qameta.allure.testng.AllureTestNg;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import utils.JsonUtil;
import utils.RequestSpecBuilderUtil;

/**
 * Test class for creating user via REST API.
 * This class contains comprehensive test scenarios for user creation functionality,
 * including data loading, request construction, API execution, and response validation.
 */
@Listeners({AllureTestNg.class})
public class CreateUserTest {

    /** Logger instance for recording test execution details */
    private static final Logger logger = LogManager.getLogger(CreateUserTest.class);

    /** Buffer to capture log messages for Allure report attachments */
    private static final StringBuilder logBuffer = new StringBuilder();

    /**
     * Main test method for user creation workflow.
     * Executes complete user creation process including data preparation,
     * API request execution, and comprehensive response validation.
     */
    @Test
    public void testCreateUser() {
        Map<String, Object> userData = Allure.step("Load test data from JSON configuration file", () -> readUserDataFromJson());
        String name = (String) userData.get("name");
        String job = (String) userData.get("job");

        String requestBody = Allure.step("Construct JSON payload for user creation", () -> createRequestBody(name, job));

        Response response = Allure.step("Execute HTTP POST request to create new user", () -> sendPostRequest(requestBody));

        Allure.step("Validate HTTP response status code", () -> {
            verifyStatusCode(response);
            return null;
        });

        Allure.step("Validate response time is less than 1000ms", () -> {
            verifyResponseTime(response);
            return null;
        });

        Allure.step("Verify response payload content", () -> {
            verifyResponseBody(response, name, job);
            return null;
        });
    }

    /**
     * Loads test data from JSON configuration file.
     * Reads user information including name and job details from the test data file.
     * Logs the data loading process and attaches logs to Allure report.
     *
     * @return Map containing user data with keys "name" and "job"
     */
    public Map<String, Object> readUserDataFromJson() {
        logBuffer.setLength(0);
        String log1 = "Reading user data from JSON file";
        logger.info(log1);
        logBuffer.append(log1).append(System.lineSeparator());
        Map<String, Object> userData = JsonUtil.readJsonFile("src/testdata/userData.json");
        String log2 = "User data read successfully: " + userData;
        logger.info(log2);
        logBuffer.append(log2).append(System.lineSeparator());
        Allure.addAttachment("Step 1 Logs", "text/plain", logBuffer.toString(), ".txt");
        return userData;
    }

    /**
     * Constructs JSON payload for user creation request.
     * Builds a properly formatted JSON string containing user name and job information.
     * Logs the payload construction process and attaches logs to Allure report.
     *
     * @param name The user's name to include in the request
     * @param job The user's job title to include in the request
     * @return JSON string representing the request body
     */
    public String createRequestBody(String name, String job) {
        logBuffer.setLength(0);
        String log1 = "Creating request body for user: " + name + " with job: " + job;
        logger.info(log1);
        logBuffer.append(log1).append(System.lineSeparator());
        String requestBody = "{\n" +
                "  \"name\": \"" + name + "\",\n" +
                "  \"job\": \"" + job + "\"\n" +
                "}";
        String log2 = "Request body created: " + requestBody;
        logger.info(log2);
        logBuffer.append(log2).append(System.lineSeparator());
        Allure.addAttachment("Step 2 Logs", "text/plain", logBuffer.toString(), ".txt");
        return requestBody;
    }

    /**
     * Executes HTTP POST request to create a new user.
     * Sends the JSON payload to the user creation endpoint using RestAssured.
     * Logs the request execution and attaches request/response data to Allure report.
     *
     * @param requestBody JSON string containing user creation data
     * @return Response object containing the API response
     */
    public Response sendPostRequest(String requestBody) {
        logBuffer.setLength(0);
        String log1 = "Sending POST request to create user";
        logger.info(log1);
        logBuffer.append(log1).append(System.lineSeparator());
        Response response = RestAssured.given()
                .spec(RequestSpecBuilderUtil.getRequestSpec())
                .body(requestBody)
                .post("/api/users");
        String log2 = "POST request sent, response status: " + response.getStatusCode();
        logger.info(log2);
        logBuffer.append(log2).append(System.lineSeparator());
        Allure.addAttachment("Request Body", "application/json", requestBody, ".json");
        Allure.addAttachment("Response Body", "application/json", response.getBody().asString(), ".json");
        Allure.addAttachment("Step 3 Logs", "text/plain", logBuffer.toString(), ".txt");
        return response;
    }

    /**
     * Validates the HTTP response status code.
     * Ensures that the API response returns the expected 201 Created status code.
     * Logs the status code verification process and attaches logs to Allure report.
     *
     * @param response The Response object from the API call
     */
    public void verifyStatusCode(Response response) {
        logBuffer.setLength(0);
        String log1 = "Verifying response status code";
        logger.info(log1);
        logBuffer.append(log1).append(System.lineSeparator());
        assertEquals(response.getStatusCode(), 201);
        String log2 = "Status code verified: 201";
        logger.info(log2);
        logBuffer.append(log2).append(System.lineSeparator());
        Allure.addAttachment("Step 4 Logs", "text/plain", logBuffer.toString(), ".txt");
    }

    /**
     * Validates the API response time performance.
     * Ensures that the API response time is within acceptable performance limits (< 1000ms).
     * Logs the response time measurement and validation process for performance monitoring.
     *
     * @param response The Response object from the API call to measure response time
     */
    public void verifyResponseTime(Response response) {
        logBuffer.setLength(0);
        String log1 = "Verifying response time";
        logger.info(log1);
        logBuffer.append(log1).append(System.lineSeparator());
        long responseTime = response.getTime();
        String log2 = "Response time: " + responseTime + " ms";
        logger.info(log2);
        logBuffer.append(log2).append(System.lineSeparator());
        assert responseTime < 1000 : "Response time " + responseTime + "ms exceeds 1000ms limit";
        String log3 = "Response time validated: " + responseTime + " ms < 1000 ms";
        logger.info(log3);
        logBuffer.append(log3).append(System.lineSeparator());
        Allure.addAttachment("Step 5 Logs", "text/plain", logBuffer.toString(), ".txt");
    }

    /**
     * Validates the content of the API response payload.
     * Ensures that the response JSON contains the correct user name and job information
     * that matches the data sent in the request. Performs comprehensive data validation.
     *
     * @param response The Response object from the API call
     * @param expectedName The expected user name in the response
     * @param expectedJob The expected user job in the response
     */
    public void verifyResponseBody(Response response, String expectedName, String expectedJob) {
        logBuffer.setLength(0);
        String log1 = "Verifying response body";
        logger.info(log1);
        logBuffer.append(log1).append(System.lineSeparator());
        String responseName = response.jsonPath().getString("name");
        String responseJob = response.jsonPath().getString("job");

        assertEquals(responseName, expectedName);
        assertEquals(responseJob, expectedJob);
        String log2 = "Response body verified: name=" + responseName + ", job=" + responseJob;
        logger.info(log2);
        logBuffer.append(log2).append(System.lineSeparator());
        Allure.addAttachment("Step 6 Logs", "text/plain", logBuffer.toString(), ".txt");
    }
}
