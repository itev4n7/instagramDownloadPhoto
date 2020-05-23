package com.instagram.download.photo.customdrivers;

import com.codeborne.selenide.WebDriverRunner;
import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.net.MalformedURLException;
import java.net.URI;

public class SelenoidDriver {
    private static final Logger LOGGER = Logger.getLogger(SelenoidDriver.class);

    public static void setUp() {
        LOGGER.info("Set up selenoid");
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--start-maximized");
        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setBrowserName("chrome");
        capabilities.setVersion("81.0");
        capabilities.setCapability(ChromeOptions.CAPABILITY, options);
        capabilities.setCapability("screenResolution", "1920x1080");
        capabilities.setCapability("enableVNC", true);
        capabilities.setCapability("enableVideo", false);
        try {
            WebDriver driver = new RemoteWebDriver(
                      URI.create("http://localhost:8090/wd/hub").toURL(),
                      capabilities
            );
            LOGGER.info("set remote driver in selenide");
            WebDriverRunner.setWebDriver(driver);
        } catch (MalformedURLException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    public static void tearDown() {
        LOGGER.info("Tear down selenoid");
        WebDriverRunner.closeWebDriver();
    }
}
