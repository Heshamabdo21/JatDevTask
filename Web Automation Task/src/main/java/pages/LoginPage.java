package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class LoginPage extends BasePage{

    private final By emailInput = By.id("email");
    private final By passwordInput = By.id("password");

    private final By loginButton = By.xpath("//input[@type='submit']");
    public LoginPage(WebDriver driver) {
        super(driver);
    }

    public LoginPage enterEmailAddress (String emailAddress) {
        waitForElementToBeVisible(emailInput);
        findElement(emailInput).sendKeys(emailAddress);
        return this;
    }

    public LoginPage enterPassword (String passowrd) {
        waitForElementToBeVisible(passwordInput);
        findElement(passwordInput).sendKeys(passowrd);
        return this;
    }

    public LoginPage Login () {
        findElement(loginButton).click();
        return this;
    }


}
