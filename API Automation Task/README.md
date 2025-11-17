# API Automation Testing Framework

A comprehensive Java-based framework for automated API testing using RestAssured, designed for testing RESTful web services with robust reporting and logging capabilities.

## 🛠️ Tools & Technologies

### Core Framework
- **Java 21** - Programming language
- **Maven** - Build automation and dependency management
- **TestNG** - Testing framework for organizing and running tests

### API Testing
- **RestAssured** (v5.5.0) - Java DSL for testing REST web services
- **Jackson** (v2.15.2) - JSON processing library for parsing and serialization

### Reporting & Logging
- **Allure** (v2.21.0) - Advanced test reporting framework with detailed test execution visualization
- **Log4j2** (v2.24.3) - Logging framework with custom appender for test report integration

## 📁 Project Structure

```
api-jatdev/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   ├── config/
│   │   │   │   └── ConfigurationManager.java    # Configuration property loader
│   │   │   └── utils/
│   │   │       ├── JsonUtil.java                # JSON file operations utility
│   │   │       ├── LogAppender.java             # Custom Log4j2 appender for Allure reports
│   │   │       └── RequestSpecBuilderUtil.java  # RestAssured request specification builder
│   │   └── resources/
│   │       ├── config.properties                # Application configuration properties
│   │       └── log4j2.xml                       # Logging configuration
│   └── test/
│       ├── java/
│       │   └── tests/
│       │       └── CreateUserTest.java          # User creation API test suite
│       └── testdata/
│           └── userData.json                     # Test data for user creation scenarios
├── pom.xml                                       # Maven project configuration
├── target/                                        # Build output directory
└── README.md                                      # Project documentation
```

## 🔧 Configuration

### config.properties
```properties
base.url=https://reqres.in
timeout=10
log.level=INFO
```

- `base.url`: Base URL for the API under test
- `timeout`: Default timeout for API requests (in seconds)
- `log.level`: Logging level for test execution

## 🚀 Getting Started

### Prerequisites
- Java 21 or higher
- Maven 3.6 or higher

### Installation
1. Clone the repository:
```bash
git clone <repository-url>
cd api-jatdev
```

2. Install dependencies:
```bash
mvn clean install
```

### Running Tests

#### Run All Tests
```bash
mvn test
```

#### Run Specific Test Class
```bash
mvn test -Dtest=CreateUserTest
```

#### Run Tests with Specific Groups
```bash
mvn test -Dgroups="smoke,regression"
```

### Generating Allure Reports

#### Generate and Serve Allure Report
```bash
mvn allure:serve
```

#### Generate Allure Report Only
```bash
mvn allure:report
```

The Allure report will be generated in the `target/site/allure-maven-plugin/` directory.

## 📊 Test Coverage

### Current Test Suite
- **CreateUserTest**: Comprehensive user creation API testing
  - Data loading from JSON files
  - Request payload construction
  - HTTP POST request execution
  - Response status code validation (201 Created)
  - Response time performance validation (< 1000ms)
  - Response payload content validation

### Test Features
- **Allure Integration**: Detailed step-by-step reporting with attachments
- **Logging**: Comprehensive logging with custom appender for report integration
- **Data-Driven**: JSON-based test data management
- **Configuration Management**: Externalized configuration for different environments
- **Request Specification**: Reusable request configurations for consistent API calls

## 🏗️ Framework Architecture

### Utility Classes

#### ConfigurationManager
Centralized configuration management for loading properties from `config.properties`. Provides static methods for accessing configuration values throughout the application.

#### JsonUtil
Utility class for JSON file operations using Jackson ObjectMapper. Handles reading JSON files and converting them to Java Map objects for test data management.

#### RequestSpecBuilderUtil
Creates standardized RestAssured RequestSpecification objects with common configurations like base URI, content type, and authentication headers.

#### LogAppender
Custom Log4j2 appender that captures log messages in memory for attachment to Allure test reports, enabling detailed logging within test execution steps.

### Test Structure
Tests follow a modular approach with separate methods for:
- Data preparation
- Request construction
- API execution
- Response validation
- Logging and reporting

## 📝 Writing New Tests

1. Create a new test class in `src/test/java/tests/`
2. Extend with TestNG annotations (`@Test`, `@Listeners`)
3. Use utility classes for common operations
4. Add Allure steps for detailed reporting
5. Include comprehensive logging

### Example Test Structure
```java
@Test
public void testNewFeature() {
    // Data preparation
    Map<String, Object> testData = JsonUtil.readJsonFile("path/to/data.json");

    // Request construction
    String requestBody = createRequestBody(testData);

    // API execution
    Response response = RestAssured.given()
        .spec(RequestSpecBuilderUtil.getRequestSpec())
        .body(requestBody)
        .post("/api/endpoint");

    // Validation
    verifyStatusCode(response);
    verifyResponseBody(response, testData);
}
```

## 🔍 Logging

The framework uses Log4j2 with a custom appender that captures logs for Allure report integration. Logs are categorized by test steps and attached as text files to the test reports.

### Log Configuration
- Console logging for development
- Custom LogAppender for report integration
- Configurable log levels per package

## 📈 CI/CD Integration

The framework is designed to integrate with CI/CD pipelines:
- Maven-based build process
- TestNG XML suites for test execution
- Allure reports for test results visualization
- Configurable environments through properties
