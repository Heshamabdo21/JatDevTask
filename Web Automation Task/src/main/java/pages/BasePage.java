package pages;

import java.time.Duration;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.WebDriverWait;

public class BasePage {
    protected WebDriver driver;
    protected final int TIMOUTINSECONDS = 20;

    public BasePage(WebDriver driver) {
        this.driver = driver;
    }

    protected  void waitForElementToBeVisible(By locator) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(TIMOUTINSECONDS));
        wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

        protected  void waitForElementToBeClickable(By locator) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(TIMOUTINSECONDS));
        wait.until(ExpectedConditions.elementToBeClickable(locator));
    }

    public WebElement waitForElementFluently(By locator, WebDriver driver) {
        return new FluentWait<>(driver)
                .withTimeout(Duration.ofSeconds(10))
                .pollingEvery(Duration.ofMillis(200))
//                .ignoring(TimeoutException.class)
                .ignoring(StaleElementReferenceException.class)
                .until(driver1 -> driver1.findElement(locator));
    }

    protected void waitForElementsToBePresent(By locator) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(TIMOUTINSECONDS));
        wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(locator));
    }

    protected void waitForElementsToBeInvisibile(By locator) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(TIMOUTINSECONDS));
        wait.until(ExpectedConditions.invisibilityOfElementLocated(locator));
    }
    protected WebElement findElement(By locator) {
        waitForElementToBeVisible(locator);
        return driver.findElement(locator);
    }

    protected boolean ElementisDisplayed(By locator) {
        try {
            driver.findElement(locator);
            return true;
        } catch (org.openqa.selenium.NoSuchElementException e) {
            return false;
        }
       // return driver.findElement(locator).isDisplayed();
    }

    protected List<WebElement> findElements(By locator) {
        waitForElementsToBePresent(locator);
        return driver.findElements(locator);
    }

    protected void waitImplicitly (int duration) {
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(duration));
    }

    protected void reloadPage () {
        driver.navigate().refresh();
    }

    protected void clickElementWithJS(By locator) {
        WebElement element = findElement(locator);
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].click();", element);
    }

    protected void clickElement(By locator) {
        WebElement element = findElement(locator);
        waitForElementsToBePresent(locator);
        waitForElementToBeClickable(locator);
        element.click();

    }
}
