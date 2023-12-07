import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import org.marinb.automation.config.WebDriverFactory;
import org.marinb.automation.pages.CalculatorFormPage;
import org.marinb.automation.pages.HomePage;
import org.marinb.automation.pages.YopMailPage;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;

import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

public class GCPPricingCalculatorTest {
    private static final Logger logger = LogManager.getLogger(GCPPricingCalculatorTest.class);
    private static WebDriver driver;
    private static Properties properties = new Properties();

    @BeforeClass
    public static void setUp() {
        try{
            properties.load(new FileReader("src/main/resources/context.properties"));
        } catch (IOException exception) {
            logger.error("Properties weren't set");
        }
        driver = WebDriverFactory.createDriver(properties.getProperty("browser"));
        logger.info("Driver was created and set up");
    }




    @Test
    public void verifyEstimatedCostViaEmailAndPage() {
        WebDriver driver = WebDriverFactory.createDriver("chrome");
        HomePage homePage = new HomePage(driver);

        //Completing form with specified parameters
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

        //Getting estimated cost from page
        String estimatedCostInPage = calculatorFormPage.getEstimatedCost();

        //Generating random email and copying it to clipboard
        YopMailPage yopMailPage = new YopMailPage(driver).openPage();
        String email = yopMailPage.getCopiedEmail();

        //Filling email field to get estimated cost via email
        ((CalculatorFormPage) calculatorFormPage.switchTab())
                .switchToFormIframe()
                .clickEmailEstimateButton()
                .fillEmailAndSubmit(email);

        //Checking inbox and getting estimated cost from email
        String estimatedCostInEmail = ((YopMailPage) yopMailPage.switchTab())
                .checkInbox()
                .getEstimatedCost();

        Assert.assertEquals(estimatedCostInPage.split(" ")[4], estimatedCostInEmail.split(" ")[4]);

    }

    @AfterTest
    public void after () {
        driver.close();
        driver.quit();
        logger.info("Driver was closed");
    }


}
