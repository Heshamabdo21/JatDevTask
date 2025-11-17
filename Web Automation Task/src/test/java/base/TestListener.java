package base;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.logging.LogEntries;
import org.openqa.selenium.logging.LogEntry;
import org.testng.ITestListener;
import org.testng.ITestResult;

import io.qameta.allure.Allure;

/**
 * TestNG listener class that captures test execution events and attaches relevant information to Allure reports.
 * This listener handles test failures, successes, and completion to provide comprehensive debugging information.
 */
public class TestListener implements ITestListener {

    /**
     * Called when a test method fails.
     * Captures screenshot, page source, and browser logs for debugging failed tests.
     * @param result The test result containing information about the failed test
     */
    @Override
    public void onTestFailure(ITestResult result) {
        // Get the test class instance
        Object testClass = result.getInstance();
        if (testClass instanceof BaseTest baseTest) {
            WebDriver driver = baseTest.driver;
            if (driver != null) {
                // Capture screenshot of the failure state
                byte[] screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
                Allure.addAttachment("Screenshot on failure", "image/png", new ByteArrayInputStream(screenshot), "screenshot.png");

                // Attach the current page HTML source
                String pageSource = driver.getPageSource();
                Allure.addAttachment("Page Source", pageSource);

                // Collect and attach browser console logs
                LogEntries logEntries = driver.manage().logs().get("browser");
                StringBuilder logs = new StringBuilder();
                for (LogEntry entry : logEntries) {
                    logs.append(entry.getLevel()).append(": ").append(entry.getMessage()).append("\n");
                }
                Allure.addAttachment("Browser Logs", logs.toString());
            }
        }
    }

    /**
     * Called when a test method succeeds.
     * Optionally captures a screenshot of the success state.
     * @param result The test result containing information about the successful test
     */
    @Override
    public void onTestSuccess(ITestResult result) {
        // Get the test class instance
        Object testClass = result.getInstance();
        if (testClass instanceof BaseTest baseTest) {
            WebDriver driver = baseTest.driver;
            if (driver != null) {
                // Capture screenshot of the success state
                byte[] screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
                Allure.addAttachment("Screenshot on success", "image/png", new ByteArrayInputStream(screenshot), "screenshot.png");
            }
        }
    }

    /**
     * Called when the test suite finishes execution.
     * Attaches the complete test execution log file to the Allure report.
     * @param context The test context containing information about the test suite
     */
    @Override
    public void onFinish(org.testng.ITestContext context) {
        // Locate the test execution log file
        File logFile = new File("target/test-logs/test.log");
        if (logFile.exists()) {
            try {
                // Attach the complete log file to Allure report
                Allure.addAttachment("Test Execution Logs", new FileInputStream(logFile));
            } catch (FileNotFoundException e) {
                // Silently ignore if log file cannot be read
            }
        }
    }
}
