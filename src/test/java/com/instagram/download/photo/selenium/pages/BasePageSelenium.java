package com.instagram.download.photo.selenium.pages;

import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;

public class BasePageSelenium {
    protected static final Logger LOGGER = Logger.getLogger(BasePageSelenium.class);
    protected WebDriver driver;
    protected WebDriverWait wait;

    public BasePageSelenium(WebDriver driver) {
        this.driver = driver;
        wait = new WebDriverWait(driver, 5000);
        PageFactory.initElements(driver, this);
    }
}
