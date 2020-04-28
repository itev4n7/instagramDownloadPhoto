package com.instagram.download.photo.selenium.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

public class BasePageSelenium {
    WebDriver driver;
    WebDriverWait wait;

    public BasePageSelenium(WebDriver driver) {
        this.driver = driver;
        wait = new WebDriverWait(driver, 5000);
    }
}
