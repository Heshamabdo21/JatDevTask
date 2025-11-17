package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.Select;

public class CheckoutPage extends BasePage{

    public CheckoutPage (WebDriver driver) {
        super(driver);
    }

    public CheckoutPage ProceedToFirstCheckoutPage () {
        By proceedToCheckoutButton1 = By.xpath("//button[@data-test='proceed-1']");
        clickElement(proceedToCheckoutButton1);
        return this;
    }
    public CheckoutPage ProceedToSecondCheckoutPage () {
        By proceedToCheckoutButton2 = By.xpath("//button[@data-test='proceed-2']");
        clickElement(proceedToCheckoutButton2);
        return this;
    }

    public CheckoutPage ProceedToThirdCheckoutPage () {
        By proceedToCheckoutButton3 = By.xpath("//button[@data-test='proceed-3']");
        clickElement(proceedToCheckoutButton3);
        return this;
    }

    public CheckoutPage FillBillingThirdCheckoutPage (String street, String city, String state, String country, String postal_code) {
        By streetInput = By.id("street");
        By cityInput = By.id("city");
        By stateInput = By.id("state");
        By countryInput = By.id("country");
        By postalCodeInput = By.id("postal_code");
        findElement(streetInput).clear();
        findElement(streetInput).sendKeys(street);
        findElement(cityInput).clear();
        findElement(cityInput).sendKeys(city);
        findElement(stateInput).clear();
        findElement(stateInput).sendKeys(state);
        findElement(countryInput).clear();
        findElement(countryInput).sendKeys(country);
        findElement(postalCodeInput).clear();
        findElement(postalCodeInput).sendKeys(postal_code);
        return this;
    }

    public CheckoutPage choosePaymentMethod () {
        By choosePaymentMethodDropDown = By.id("payment-method");
        Select paymentMethodDropDown = new Select(findElement(choosePaymentMethodDropDown));
        waitForElementToBeVisible(choosePaymentMethodDropDown);
        paymentMethodDropDown.selectByValue("cash-on-delivery");
        return this;
    }

    public CheckoutPage finilizeOrder () {
        By confirmOrder = By.xpath("//button[@data-test='finish']");
        findElement(confirmOrder).click();
        return this;
    }

    public String getpaymentConfirmationMessage () {
        By successMessage = By.xpath("//div[@data-test='payment-success-message']");
        return findElement(successMessage).getText();
    }

    public String getorderConfirmationMessage () {
        By successMessage = By.xpath("//*[@id=\"order-confirmation\"]");
        return findElement(successMessage).getText();
    }

}
