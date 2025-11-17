package pages;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;

public class HomePage extends BasePage {
    public HomePage (WebDriver driver) {
        super(driver);
    }
    private final By signInButton = By.partialLinkText("Sign in");

    public LoginPage clickOnSignInButton () {
        findElement(signInButton).click();
        return new LoginPage(driver);
    }
    public ProductPage clickOnProduct () {
        By products = By.xpath("//a[contains(@data-test ,'product')]");
        List<WebElement> productsElem = findElements(products);
        productsElem.get(1).click();
        return new ProductPage(driver);
    }

    public HomePage AssertMenubuttonIsDisplayed()
    {
        By menuButton = By.xpath("//*[@id=\"menu\"]");
        Assert.assertTrue(findElement(menuButton).isDisplayed());
        return this;
    }
    public HomePage returnHomePage () {
        By homeButton = By.xpath("//*[@id=\"navbarSupportedContent\"]/ul/li[1]/a");
        findElement(homeButton).click();
        return this;
    }

    public CheckoutPage clickOnCart () {
        By cartButton = By.xpath("//a[@data-test='nav-cart']");
        findElement(cartButton).click();
        return new CheckoutPage(driver);
    }

    public HomePage AssertclickOnCartIsNotDisplayed()
    {
        By cartButton = By.xpath("//a[@data-test='nav-cart']");
        waitForElementsToBeInvisibile(cartButton);
        Assert.assertFalse(ElementisDisplayed(cartButton));
        return this;
    }





}
