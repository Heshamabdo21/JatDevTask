# E-Commerce Checkout Automation Project

This project contains automated tests for the complete e-commerce checkout process using Selenium WebDriver, TestNG, and Allure reporting.

## Table of Contents
- [Technologies and Frameworks](#technologies-and-frameworks)
- [Project Structure](#project-structure)
- [Prerequisites](#prerequisites)
- [Installation and Setup](#installation-and-setup)
- [Running Tests](#running-tests)
- [Test Coverage](#test-coverage)
- [Reporting](#reporting)
- [Configuration](#configuration)
- [Contributing](#contributing)

## Technologies and Frameworks

### Core Frameworks
- **Java 21** - Programming language
- **Maven** - Build automation and dependency management
- **TestNG** - Testing framework for test execution
- **Selenium WebDriver 4.38.0** - Browser automation
- **Allure 2.24.0** - Test reporting and visualization

### Supporting Libraries
- **Log4j 2.20.0** - Logging framework
- **Gson 2.10.1** - JSON parsing for test data
- **ChromeDriver** - Browser driver for Chrome automation

### Development Tools
- **Visual Studio Code** - IDE
- **Git** - Version control
- **Allure CLI** - Command-line interface for report generation

## Project Structure

```
jatdev-task/
├── src/
│   ├── main/
│   │   └── java/
│   │       └── pages/          # Page Object Model classes
│   │           ├── BasePage.java
│   │           ├── CheckoutPage.java
│   │           ├── HomePage.java
│   │           ├── LoginPage.java
│   │           └── ProductPage.java
│   └── test/
│       ├── java/
│       │   ├── base/           # Base test infrastructure
│       │   │   ├── BaseTest.java
│       │   │   └── TestListener.java
│       │   └── tests/          # Test classes
│       │       └── Checkout.java
│       └── resources/
│           ├── log4j2.xml      # Logging configuration
│           └── testdata.json   # Test data
├── target/                     # Build output directory
├── allure-results/             # Allure test results
├── allure-report/              # Generated Allure reports
├── pom.xml                     # Maven configuration
├── README.md                   # This file
└── .gitignore                  # Git ignore rules
```

## Prerequisites

Before running this project, ensure you have the following installed:

1. **Java Development Kit (JDK) 21**
   - Download from [Oracle JDK](https://www.oracle.com/java/technologies/javase/jdk21-archive-downloads.html) or [OpenJDK](https://openjdk.org/)

2. **Apache Maven 3.6+**
   - Download from [Maven Official Site](https://maven.apache.org/download.cgi)
   - Add Maven to your system PATH

3. **Google Chrome Browser**
   - Latest stable version recommended

4. **Allure CLI (Optional but recommended)**
   - Install via npm: `npm install -g allure-commandline`
   - Or download from [Allure Releases](https://github.com/allure-framework/allure2/releases)

5. **Git**
   - For version control operations

## Installation and Setup

1. **Clone the repository:**
   ```bash
   git clone <repository-url>
   cd jatdev-task
   ```

2. **Verify Java and Maven installation:**
   ```bash
   java -version
   mvn -version
   ```

3. **Install dependencies:**
   ```bash
   mvn clean install
   ```

4. **Verify ChromeDriver:**
   - Maven will automatically download the appropriate ChromeDriver version
   - Ensure Chrome browser is installed and up to date

## Running Tests

### Run All Tests
```bash
mvn clean test
```

### Run Tests with Package Phase (Recommended)
```bash
mvn clean package
```
This command will:
- Clean old build artifacts
- Delete previous Allure results and reports
- Compile the code
- Run all tests
- Generate Allure report
- Start Allure web server automatically

### Run Specific Test Class
```bash
mvn test -Dtest=Checkout
```

### Run Tests with Custom Maven Options
```bash
mvn test -Dmaven.test.failure.ignore=true  # Continue on test failures
mvn test -Dtestng.parallel=methods -Dtestng.threadCount=3  # Parallel execution
```

## Test Coverage

### Test Scenarios Covered

The automation suite covers the complete e-commerce checkout workflow:

1. **User Authentication**
   - Navigate to application
   - Click sign-in button
   - Enter valid credentials
   - Verify successful login

2. **Product Selection**
   - Browse products
   - Select a product
   - Add to shopping cart
   - Verify cart contents

3. **Checkout Process**
   - Navigate to cart
   - Proceed through checkout steps
   - Enter billing information
   - Select payment method
   - Complete order

4. **Order Verification**
   - Verify payment confirmation
   - Check order details
   - Validate success messages
   - Confirm cart state after purchase

### Test Data
- Test data is externalized in `src/test/resources/testdata.json`
- Supports multiple test scenarios with different user credentials and billing information
- Easily maintainable and modifiable for different test environments

### Assertions and Validations
- UI element presence and visibility
- Text content verification
- Form submission success
- Error handling and edge cases

## Reporting

### Allure Reports
The project generates comprehensive Allure reports with:

- **Step-by-step execution details** with timestamps
- **Screenshots** captured after each major action
- **Log files** attached for each test step
- **Browser console logs** for debugging JavaScript errors
- **Page source** for HTML inspection on failures
- **Test execution timeline** and duration
- **Failure analysis** with detailed error information

### Accessing Reports

After running `mvn package`, the Allure report will automatically open in your default browser. Alternatively:

1. **Manual access:** Open `allure-report/index.html` in a web browser
2. **Allure CLI:** Run `allure serve allure-results` to start a local server

### Report Features
- Interactive test execution timeline
- Step-by-step breakdown with attachments
- Historical test trends
- Failure categorization
- Environment information
- Test execution statistics

## Configuration

### TestNG Configuration
- Located in `pom.xml` under `<properties>` and `<configuration>`
- Test listeners configured for Allure integration
- Parallel execution support available

### Logging Configuration
- Log4j2 configuration in `src/test/resources/log4j2.xml`
- Logs written to console and `target/test-logs/test.log`
- Configurable log levels (currently set to INFO)

### Browser Configuration
- Chrome browser with maximized window
- Automatic ChromeDriver management via WebDriverManager
- Headless mode can be enabled by modifying `BaseTest.java`

### Allure Configuration
- Results stored in `allure-results/` directory
- Reports generated in `allure-report/` directory
- Automatic cleanup of old results before each test run

### Code Standards
- Follow Java naming conventions
- Add JavaDoc comments for all public methods
- Include unit tests for new functionality
- Update README for any configuration changes

### Adding New Tests
1. Create new test methods in appropriate test classes
2. Use Page Object Model for UI interactions
3. Add test data to `testdata.json` if needed
4. Include appropriate assertions and verifications
5. Update this README if test coverage changes

## Troubleshooting

### Common Issues

1. **ChromeDriver compatibility:**
   - Ensure Chrome browser is updated
   - Maven will automatically manage ChromeDriver version

2. **Test failures:**
   - Check application availability
   - Verify test data in `testdata.json`
   - Review Allure reports for detailed failure information

3. **Allure report not opening:**
   - Install Allure CLI: `npm install -g allure-commandline`
   - Manually run: `allure serve allure-results`

4. **Build failures:**
   - Run `mvn clean install` to ensure all dependencies
   - Check Java version compatibility