package org.marinb.automation.pages;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.marinb.automation.config.WebDriverFactory;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class HomePage extends AbstractPage{
    private static final Logger logger = LogManager.getLogger(HomePage.class);
    @FindBy(name = "q"  )
    private WebElement searchField;

    public HomePage(WebDriver driver) {
        super(driver);
    }

    @Override
    public HomePage openPage() {
        logger.info("Opening home page");
        driver.get(BASE_URL);
        if(driver instanceof ChromeDriver){
            closeAdblockTab();
        }
        driver.manage().window().maximize();
        return this;
    }

    private void closeAdblockTab() {
        try{
            createExplicitWait().until(ExpectedConditions.numberOfWindowsToBe(2));
            String currentTab = driver.getWindowHandle();
            for (String tab : driver.getWindowHandles()) {
                if (!tab.equals(currentTab)) {
                    driver.switchTo().window(tab);
                    driver.close();
                }
            }
            driver.switchTo().window(currentTab);
        }catch (Exception e){}
    }

    public ResultsPage search(String text) {
        click(searchField);
        searchField.sendKeys(text, Keys.ENTER);
        return new ResultsPage(driver);
    }



}
