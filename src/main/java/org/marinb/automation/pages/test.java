package org.marinb.automation.pages;

import org.marinb.automation.config.WebDriverFactory;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

public class test {
    public static void main(String[] args) {
        WebDriver driver = WebDriverFactory.createDriver("firefox");
        HomePage homePage = new HomePage(driver);
        CalculatorFormPage calculatorFormPage = homePage.openPage().search("Google Cloud Platform Pricing Calculator")
                .openCalculatorPage()
                .switchToFormIframe()
                .fillNumberOfInstances(4)
                .selectOperatingSystem("free")
                .selectProvisioningModel("regular")
                .selectMachineFamily("gp")
                .selectSeries("n1")
                .selectMachineType("CP-COMPUTEENGINE-VMIMAGE-N1-STANDARD-8")
                .clickAddGPUsCheckbox().selectGPUType("NVIDIA_TESLA_V100")
                .selectNumberOfGPUs(1)
                .selectLocalSSD(2)
                .selectLocation("europe-west3")
                .selectUsage(1)
                .clickAddToEstimateButton();
        String estimatedCostInPage = calculatorFormPage.getEstimatedCost();

        YopMailPage yopMailPage = new YopMailPage(driver).openPage();
        String email = yopMailPage.getCopiedEmail();

        ((CalculatorFormPage) calculatorFormPage.switchTab())
                .switchToFormIframe()
                .clickEmailEstimateButton()
                .fillEmailAndSubmit(email);

        String estimatedCostInEmail = ((YopMailPage) yopMailPage.switchTab())
                .checkInbox()
                .getEstimatedCost();
        System.out.println(estimatedCostInEmail.split(" ")[4]);

        driver.close();
        driver.quit();
    }
}
