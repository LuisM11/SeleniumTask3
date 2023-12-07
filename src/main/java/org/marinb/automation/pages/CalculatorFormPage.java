package org.marinb.automation.pages;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindAll;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.List;

public class CalculatorFormPage extends AbstractPage{

    public CalculatorFormPage(WebDriver driver) {
        super(driver);
    }

    private static final Logger logger = LogManager.getLogger(CalculatorFormPage.class);


    @FindBy(css="#cloud-site > devsite-iframe > iframe")
    private WebElement iframe;
    @FindBy(xpath = "//md-card-content[@id='mainForm']//label[contains(text(),'Number of instances')]/following-sibling::input")    //@FindBy(css = "#mainForm form input#input_101")
    private WebElement numberOfInstances;

    @FindBy(xpath = "//md-card-content[@id='mainForm']//label[text()='Operating System / Software']/following-sibling::md-select")       //@FindBy(id = "select_114")
    private WebElement operatingSystemSelect;

    @FindBy(xpath = "//md-card-content[@id='mainForm']//label[text()='Provisioning model']/following-sibling::md-select")       //@FindBy(id = "select_114")
    private WebElement provisioningModelSelect;

    @FindBy(xpath = "//md-card-content[@id='mainForm']//label[text()='Machine Family']/following-sibling::md-select")       //@FindBy(id = "select_114")
    private WebElement machineFamilySelect;

    @FindBy(xpath = "//md-card-content[@id='mainForm']//label[text()='Series']/following-sibling::md-select")
    private WebElement seriesSelect;

    @FindBy(xpath = "//md-card-content[@id='mainForm']//label[text()='Machine type']/following-sibling::md-select")
    private WebElement machineTypeSelect;

    @FindAll (@FindBy(xpath = "//md-card-content[@id='mainForm']//div[@class='md-label' and contains(text(),'Add GPUs')]/preceding-sibling::div"))
    private List<WebElement> addGPUsCheckbox;

    @FindBy(xpath = "//md-card-content[@id='mainForm']//md-select[@aria-label='GPU type']")
    private WebElement gpuTypeSelect;
    @FindBy(xpath = "//md-card-content[@id='mainForm']//label[text()='Number of GPUs']/following-sibling::md-select")
    private WebElement numberOfGPUsSelect;

    @FindAll(@FindBy(xpath = "//md-card-content[@id='mainForm']//md-select[contains(@aria-label,'Local SSD')]"))
    private List<WebElement> localSSDSelect;

    @FindBy(xpath = "//md-card-content[@id='mainForm']//label[text()='Datacenter location']/following-sibling::md-select")
    private WebElement locationSelect;

    @FindBy(xpath = "//md-card-content[@id='mainForm']//label[text()='Committed usage']/following-sibling::md-select")
    private WebElement usageSelect;

    @FindBy(xpath = "//button[contains(text(),'Add to Estimate') and not(@disabled='disabled')]")
    private WebElement addToEstimateButton;

    @FindBy(id = "resultBlock")
    private WebElement resultBlock;

    @FindBy(id = "Email Estimate")
    private WebElement emailEstimateButton;

    @FindBy(xpath="//form[@name='emailForm']//label[text()='Email ']/following-sibling::input")
    private WebElement emailInput;

    public CalculatorFormPage switchToFormIframe() {
        createFluentWait().until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(iframe));
        driver.switchTo().frame("myFrame");
        return this;
    }

    public CalculatorFormPage fillNumberOfInstances(int number) {
        waitElementToBeClickable(numberOfInstances);
        numberOfInstances.sendKeys(String.valueOf(number));
        return this;
    }

    private CalculatorFormPage genericSelect(String value, WebElement select) {
        scrollToElement(select);
        click(select);
        WebElement option = driver.findElement(By.xpath("//div[contains(@class,'md-active')]//md-option[@value='" + value + "']"));
        click(option);
        return this;
    }

    public CalculatorFormPage selectOperatingSystem(String value) {
        return genericSelect(value, operatingSystemSelect);
    }

    public CalculatorFormPage selectProvisioningModel(String value) {
        return genericSelect(value, provisioningModelSelect);
    }

    public CalculatorFormPage selectMachineFamily(String value) {
        return genericSelect(value, machineFamilySelect);
    }

    public CalculatorFormPage selectSeries(String value) {
        return genericSelect(value, seriesSelect);
    }

    public CalculatorFormPage selectMachineType(String value) {
        return genericSelect(value, machineTypeSelect);
    }

    public CalculatorFormPage selectGPUType(String value) {
        return genericSelect(value, gpuTypeSelect);
    }
    public CalculatorFormPage selectNumberOfGPUs(int value) {
        return genericSelect(String.valueOf(value) , numberOfGPUsSelect);
    }

    public CalculatorFormPage selectLocalSSD(int value) {
        return genericSelect(String.valueOf(value), localSSDSelect.get(0));
    }

    public CalculatorFormPage selectLocation(String value) {
        return genericSelect(value, locationSelect);
    }

    public CalculatorFormPage selectUsage(int value) {
        return genericSelect(String.valueOf(value), usageSelect);
    }

    public CalculatorFormPage clickAddGPUsCheckbox() {
        scrollToElement(addGPUsCheckbox.get(0));
        click(addGPUsCheckbox.get(0));
        return this;
    }

    public CalculatorFormPage clickAddToEstimateButton() {
        scrollToElement(addToEstimateButton);
        click(addToEstimateButton);
        return this;
    }

    public CalculatorFormPage clickEmailEstimateButton() {
        scrollToElement(emailEstimateButton);
        click(emailEstimateButton);
        return this;
    }


    @Override
    protected AbstractPage openPage() {
        return null;
    }

    public CalculatorFormPage fillEmailAndSubmit(String email) {
        waitElementToBeVisible(emailInput);
        scrollToElement(emailInput);
        emailInput.sendKeys(email);
        WebElement submitButton = driver.findElement(By.xpath("//form[@name='emailForm']//button[contains(text(),'Send Email')]"));
        scrollToElement(submitButton);
        CalculatorFormPage.sleep(1);
        click(submitButton);
        return this;
    }

    public String getEstimatedCost() {
        waitElementToBeVisible(resultBlock);
        String totalEstimatedCost = resultBlock.findElement(By.xpath("//div[@class='cpc-cart-total']/h2/b")).getText();
        logger.info("Getting estimated cost in page -> " + totalEstimatedCost);
        return totalEstimatedCost;
    }
}
