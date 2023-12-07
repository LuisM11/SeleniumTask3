package org.marinb.automation.pages;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.IOException;

public class YopMailPage extends AbstractPage{
    private static final Logger logger = LogManager.getLogger(YopMailPage.class);
    @FindBy(id = "geny")
    private WebElement generatedEmail;
    @FindBy(id="cprnd")
    private WebElement copyButton;
    @FindBy(xpath = "//span[text()='Check Inbox']/..")
    private WebElement checkInboxButton;

    @FindBy(id = "ifmail")
    private WebElement inboxIframe;

    @FindBy(id="ad_position_box")
    private WebElement adPositionBox;

    public YopMailPage(WebDriver driver) {
        super(driver);
    }

    private YopMailPage refreshInbox(){
        YopMailPage.sleep(2);
        driver.navigate().refresh();
        logger.info("Refreshing inbox");
        return this;
    }

    private YopMailPage switchToInboxIframe(){
        createFluentWait().until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(inboxIframe));
        return this;
    }

    public String getEstimatedCost(){
        refreshInbox();
        switchToInboxIframe();
        String estimatedCost = createExplicitWait().until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//h2[contains(text(),'Estimated Monthly Cost:')]"))).getText();
        logger.info("Getting estimated cost in Email -> " + estimatedCost);
        return estimatedCost;
    }

    public String getCopiedEmail(){
        waitElementToBeClickable(copyButton);
        scrollToElement(copyButton);
        click(copyButton);
        logger.info("Email was copied to clipboard");
        YopMailPage.sleep(1);
        Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
        try {
            String result = clipboard.getData(DataFlavor.stringFlavor).toString();
            logger.info("Getting email from clipboard -> " + result);
            return result;
        } catch (Exception e) {
            logger.error("Error while getting copied email -> " + e.getMessage());
        }
        return null;
    }

    public YopMailPage checkInbox(){
        click(checkInboxButton);
        logger.info("Checking inbox");
        return this;
    }


    @Override
    public YopMailPage openPage() {
        openNewTab("https://yopmail.com/email-generator");
        switchTab();
        return this;
    }

    private YopMailPage closeAd(){
        try {
            createFluentWait().until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(By.id("aswift_3")));
            driver.switchTo().frame("ad_iframe");
            if(adPositionBox.isDisplayed()){
                logger.info("Ad is displayed");
            }
            WebElement closeButton = driver.findElement(By.id("dismiss-button"));
            click(closeButton);
            logger.info("Ad was closed");
        } catch (Exception e) {
        }
        return this;
    }


}
