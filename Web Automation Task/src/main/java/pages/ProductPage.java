package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
public class ProductPage extends BasePage{

    private final By addToCartBttn = By.xpath("//*[@id=\"btn-add-to-cart\"]");
    public ProductPage (WebDriver driver) {
        super(driver);
    }

    public ProductPage addProductToCart() {
        clickElement(addToCartBttn);
        return this;
    }

    public ProductPage AssertProductIsAdded()
    {
        By successMessage = By.xpath("//*[@id=\"toast-container\"]");
        Assert.assertTrue(findElement(successMessage).isDisplayed());
        return this;
    }
}
