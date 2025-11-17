package base;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

import io.qameta.allure.Allure;

/**
 * Base test class providing common setup, teardown, and utility methods for Selenium WebDriver tests.
 * This class initializes the ChromeDriver, manages the WebDriver lifecycle, and provides helper methods
 * for attaching screenshots and logs to Allure reports.
 */
public class BaseTest {
    /** Logger instance for logging test execution details */
    protected static final Logger logger = LogManager.getLogger(BaseTest.class);

    /** WebDriver instance for browser automation */
    protected WebDriver driver;

    /**
     * Initializes the Chrome WebDriver before each test method.
     * Creates a new ChromeDriver instance if not already initialized and maximizes the browser window.
     */
    @BeforeMethod
    public void initializeDriver() {
        if (driver == null) {
            // Create new ChromeDriver instance
            driver = new ChromeDriver();
            // Maximize browser window for consistent test execution
            driver.manage().window().maximize();
        }
    }

    /**
     * Cleans up the WebDriver after each test method.
     * Quits the browser and sets the driver to null for proper cleanup.
     */
    @AfterMethod
    public void quitDriver() {
        if (driver != null) {
            // Close browser and clean up resources
            driver.quit();
            driver = null;
        }
    }

    /**
     * Captures a screenshot of the current browser state and attaches it to the Allure report.
     * @param name The name for the screenshot attachment
     */
    protected void attachScreenshot(String name) {
        if (driver != null) {
            // Capture screenshot as byte array
            byte[] screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
            // Attach screenshot to Allure report
            Allure.addAttachment(name, "image/png", new ByteArrayInputStream(screenshot), "screenshot.png");
        }
    }

    /**
     * Attaches the test execution log file to the Allure report for the specified step.
     * @param stepName The name of the step for which logs are being attached
     */
    protected void attachLogs(String stepName) {
        try {
            // Locate the log file
            File logFile = new File("target/test-logs/test.log");
            if (logFile.exists()) {
                // Attach log file to Allure report
                Allure.addAttachment(stepName + " Logs", new FileInputStream(logFile));
            }
        } catch (FileNotFoundException e) {
            // Silently ignore if log file is not found
        }
    }
}
