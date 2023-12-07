package org.marinb.automation.pages;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.Set;

public abstract class AbstractPage {
    public static final String BASE_URL = "https://cloud.google.com/";
    private static final Logger logger = LogManager.getLogger(AbstractPage.class);
    protected final int WAIT_TIMEOUT_SECONDS = 6;
    protected WebDriver driver;
    @FindBy(css = "#hideSlideBanner > svg")
    protected WebElement closeButtonSmartBanner;

    protected abstract AbstractPage openPage();


    protected AbstractPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(this.driver, this);
    }

    protected FluentWait<WebDriver> createFluentWait() {
        return new FluentWait<>(driver).
                withTimeout(Duration.ofSeconds(WAIT_TIMEOUT_SECONDS)).
                pollingEvery(Duration.ofMillis(500)).
                ignoring(NotFoundException.class);
    }
    protected WebDriverWait createExplicitWait() {
        return new WebDriverWait(driver, Duration.ofSeconds(WAIT_TIMEOUT_SECONDS));
    }
    protected void waitElementToBeClickable(WebElement element) {
        try{
            createExplicitWait().until(ExpectedConditions.elementToBeClickable(element));
        } catch (TimeoutException e) {
            logger.error("Timeout exceeded waiting element to be clickable -> " + e.getMessage() );
        } catch (StaleElementReferenceException e) {
            logger.error("Element is no longer attached to DOM -> " + e.getMessage() );
        }
    }
    protected void waitElementToBeVisible(WebElement element) {
        try {
              createExplicitWait().until(ExpectedConditions.visibilityOf(element));
        } catch (TimeoutException e) {
              logger.error("Timeout exceeded waiting element to be visible -> " + e.getMessage() );
        } catch (StaleElementReferenceException e) {
              logger.error("Element is no longer attached to DOM -> " + e.getMessage() );
       }
    }

    protected void click(WebElement element) {
        waitElementToBeVisible(element);
        waitElementToBeClickable(element);
        try{
            logger.info("Clicking on element " + element);
            element.click();
            logger.info("Element " +  element + " was clicked");
        }
        catch (NoSuchElementException e){
            logger.error("Element not Found -> " + e.getMessage() );
        }
    }

    public void scrollToElement(WebElement element) {
        try {
            Thread.sleep(350);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({ block: \"center\", behavior: \"smooth\" });", element);
//        new Actions(driver).moveToElement(element).perform();
    }

    protected void openNewTab(String url) {
        ((JavascriptExecutor) driver).executeScript("window.open('"+ url +"')");
    }

    public AbstractPage switchTab() {
        String currentTab = driver.getWindowHandle();
        String newTab = driver.getWindowHandles()
                .stream()
                .filter(handle -> !handle.equals(currentTab))
                .findFirst()
                .get();
        driver.switchTo().window(newTab);
        return this;
    }

    public static void sleep(int seconds) {
        try {
            Thread.sleep(seconds * 1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

    }






}
