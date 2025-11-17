package tests;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import base.BaseTest;
import io.qameta.allure.Allure;
import pages.CheckoutPage;
import pages.HomePage;
import pages.LoginPage;
import pages.ProductPage;

/**
 * Test class for the e-commerce checkout functionality.
 * This class contains automated tests for the complete checkout process including login, product selection,
 * cart management, and order completion. It uses TestNG for test execution and Allure for reporting.
 */
public class Checkout extends BaseTest {
    /** Logger instance for logging test execution details */
    private static final Logger testLogger = LogManager.getLogger(Checkout.class);

    /**
     * Data provider method that reads test data from a JSON file.
     * Provides test data for the checkout scenario including URLs, credentials, and expected results.
     * @return Object[][] array containing test data for each test iteration
     * @throws IOException if the test data file cannot be read
     */
    @DataProvider(name = "checkoutData")
    public Object[][] getCheckoutData() throws IOException {
        // Read the test data JSON file using try-with-resources
        try (FileReader reader = new FileReader("src/test/resources/testdata.json")) {
            JsonObject jsonObject = JsonParser.parseReader(reader).getAsJsonObject();
            JsonArray testDataArray = jsonObject.getAsJsonArray("testData");

            // Parse JSON data into test data array
            List<Object[]> data = new ArrayList<>();
            for (JsonElement element : testDataArray) {
                JsonObject testCase = element.getAsJsonObject();
                String url = testCase.get("url").getAsString();
                String username = testCase.get("username").getAsString();
                String password = testCase.get("password").getAsString();
                String street = testCase.get("street").getAsString();
                String city = testCase.get("city").getAsString();
                String state = testCase.get("state").getAsString();
                String country = testCase.get("country").getAsString();
                String postal_code = testCase.get("postal_code").getAsString();
                String expectedMessage = testCase.get("expectedMessage").getAsString();
                String invoiceexpectedMessage = testCase.get("invoiceexpectedMessage").getAsString();
                data.add(new Object[]{url, username, password, street, city, state, country, postal_code, expectedMessage,invoiceexpectedMessage});
            }
            return data.toArray(new Object[0][]);
        }
    }

    /**
     * Main test method that automates the complete e-commerce checkout scenario.
     * This test covers the entire user journey from login to order completion, including:
     * - User authentication
     * - Product selection and cart management
     * - Checkout process with billing information
     * - Payment processing and order confirmation
     * - Verification of success messages and UI state
     *
     * The test uses Allure steps for detailed reporting and attaches screenshots and logs at each step.
     *
     * @param url The application URL to test
     * @param username User login credentials
     * @param password User password
     * @param street Billing address street
     * @param city Billing address city
     * @param state Billing address state
     * @param country Billing address country
     * @param postal_code Billing address postal code
     * @param expectedMessage Expected payment confirmation message
     * @param invoiceexpectedMessage Expected invoice confirmation message
     * @throws InterruptedException if thread sleep is interrupted
     */
    @Test(dataProvider = "checkoutData")
    public void automateScenario (String url, String username, String password, String street, String city, String state, String country, String postal_code, String expectedMessage , String invoiceexpectedMessage) throws InterruptedException {
        testLogger.info("Starting checkout automation scenario");
        Allure.step("Navigate to application URL", () -> {
            testLogger.info("Navigating to URL: " + url);
            driver.get(url);
            attachScreenshot("After navigation");
            attachLogs("Navigation");
        });
        HomePage homePage = Allure.step("Initialize HomePage object", () -> {
            testLogger.info("Initializing HomePage object");
            return new HomePage(driver);
        });
        LoginPage loginPage = Allure.step("Click on Sign In button", () -> {
            testLogger.info("Clicking on Sign In button");
            LoginPage lp = homePage.clickOnSignInButton();
            attachScreenshot("After clicking sign in");
            attachLogs("Sign In");
            return lp;
        });
        Allure.step("Enter email address", () -> {
            testLogger.info("Entering email address: " + username);
            loginPage.enterEmailAddress(username);
            attachScreenshot("After entering email");
            attachLogs("Email Entry");
        });
        Allure.step("Enter password", () -> {
            testLogger.info("Entering password");
            loginPage.enterPassword(password);
            attachScreenshot("After entering password");
            attachLogs("Password Entry");
        });
        Allure.step("Click Login button", () -> {
            testLogger.info("Clicking Login button");
            loginPage.Login();
            attachScreenshot("After login");
            attachLogs("Login");
        });
        Allure.step("Verify menu button is displayed", () -> {
            testLogger.info("Verifying menu button is displayed");
            homePage.AssertMenubuttonIsDisplayed();
            attachScreenshot("After verifying menu");
            attachLogs("Menu Verification");
        });
        Allure.step("Return to home page", () -> {
            testLogger.info("Returning to home page");
            homePage.returnHomePage();
            attachScreenshot("After returning home");
            attachLogs("Home Return");
        });
        ProductPage productPage = Allure.step("Click on product", () -> {
            testLogger.info("Clicking on product");
            ProductPage pp = homePage.clickOnProduct();
            attachScreenshot("After clicking product");
            attachLogs("Product Click");
            return pp;
        });
        Allure.step("Add product to cart", () -> {
            testLogger.info("Adding product to cart");
            productPage.addProductToCart();
            attachScreenshot("After adding to cart");
            attachLogs("Add to Cart");
        });
        Allure.step("Verify product is added to cart", () -> {
            testLogger.info("Verifying product is added to cart");
            productPage.AssertProductIsAdded();
            attachScreenshot("After verifying product added");
            attachLogs("Cart Verification");
        });
        Allure.step("Return to home page", () -> {
            testLogger.info("Returning to home page again");
            homePage.returnHomePage();
            attachScreenshot("After returning home again");
            attachLogs("Home Return Again");
        });
        CheckoutPage checkoutPage = Allure.step("Click on cart", () -> {
            testLogger.info("Clicking on cart");
            CheckoutPage cp = homePage.clickOnCart();
            attachScreenshot("After clicking cart");
            attachLogs("Cart Click");
            return cp;
        });
        Allure.step("Proceed to first checkout page", () -> {
            testLogger.info("Proceeding to first checkout page");
            checkoutPage.ProceedToFirstCheckoutPage();
            attachScreenshot("After proceeding to first checkout");
            attachLogs("First Checkout");
        });
        Allure.step("Proceed to second checkout page", () -> {
            testLogger.info("Proceeding to second checkout page");
            checkoutPage.ProceedToSecondCheckoutPage();
            attachScreenshot("After proceeding to second checkout");
            attachLogs("Second Checkout");
        });
        Allure.step("Fill billing information", () -> {
            testLogger.info("Filling billing information: " + street + ", " + city + ", " + state + ", " + country + ", " + postal_code);
            checkoutPage.FillBillingThirdCheckoutPage(street, city, state, country, postal_code);
            attachScreenshot("After filling billing info");
            attachLogs("Billing Info");
        });
        Allure.step("Proceed to third checkout page", () -> {
            testLogger.info("Proceeding to third checkout page");
            checkoutPage.ProceedToThirdCheckoutPage();
            attachScreenshot("After proceeding to third checkout");
            attachLogs("Third Checkout");
        });
        Allure.step("Choose payment method", () -> {
            testLogger.info("Choosing payment method");
            checkoutPage.choosePaymentMethod();
            attachScreenshot("After choosing payment");
            attachLogs("Payment Method");
        });
        Allure.step("Finalize order", () -> {
            testLogger.info("Finalizing order");
            checkoutPage.finilizeOrder();
            attachScreenshot("After finalizing order");
            attachLogs("Order Finalize");
        });
        String returnedMessage = Allure.step("Get payment confirmation message", () -> {
            testLogger.info("Getting payment confirmation message");
            String msg = checkoutPage.getpaymentConfirmationMessage();
            attachScreenshot("After getting confirmation message");
            attachLogs("Confirmation Message");
            return msg;
        });
        Allure.step("Verify payment confirmation message", () -> {
            testLogger.info("Verifying payment confirmation message: expected '" + expectedMessage + "', got '" + returnedMessage + "'");
            Assert.assertEquals(returnedMessage, expectedMessage);
            attachScreenshot("After verifying confirmation");
            attachLogs("Confirmation Verification");
        });
        Allure.step("Finalize order again", () -> {
            testLogger.info("Finalizing order again");
            checkoutPage.finilizeOrder();
            attachScreenshot("After finalizing again");
            attachLogs("Order Finalize Again");
        });
        String returnedinvoiceMessage = Allure.step("Get order confirmation message", () -> {
            testLogger.info("Getting order confirmation message");
            String msg = checkoutPage.getorderConfirmationMessage();
            attachScreenshot("After getting invoice message");
            attachLogs("Invoice Message");
            return msg;
        });
        Allure.step("Verify order confirmation message", () -> {
            testLogger.info("Verifying order confirmation message contains: " + invoiceexpectedMessage);
            Assert.assertTrue(returnedinvoiceMessage.contains(invoiceexpectedMessage));
            attachScreenshot("After verifying invoice");
            attachLogs("Invoice Verification");
        });
        Allure.step("Verify cart is not displayed", () -> {
            testLogger.info("Verifying cart is not displayed");
            homePage.AssertclickOnCartIsNotDisplayed();
            attachScreenshot("After verifying cart not displayed");
            attachLogs("Cart Not Displayed");
        });
        testLogger.info("Checkout automation scenario completed successfully");
    }
}
