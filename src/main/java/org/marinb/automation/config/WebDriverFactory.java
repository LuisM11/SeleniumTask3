package org.marinb.automation.config;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.firefox.FirefoxProfile;

import java.io.File;


public class WebDriverFactory {
    public static WebDriver createDriver(String browser) {
        switch (browser) {
            case "chrome":
                ChromeOptions chromeOptions = new ChromeOptions();
                chromeOptions.addExtensions(new File("src/test/resources/AdBlock-el-mejor-bloqueador-de-anuncios.crx"));
                return new ChromeDriver(chromeOptions);
//                return  new ChromeDriver();
            case "firefox":
                return new FirefoxDriver();
            case "edge":
                return new EdgeDriver();
            default:
                throw new IllegalArgumentException("Invalid browser name");
        }
    }

}
