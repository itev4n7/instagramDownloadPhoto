package com.instagram.download.photo.selenium.pages;

import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;

public class LoginPageSelenium extends BasePageSelenium {
    private static final String PAGE_URL = "https://www.instagram.com/accounts/login/?source=auth_switcher";

    @FindBy(how = How.NAME, using = "username")
    private WebElement usernameField;

    @FindBy(how = How.NAME, using = "password")
    private WebElement passwordField;

    public LoginPageSelenium(WebDriver driver) {
        super(driver);
    }


    public void tryToLogin(String username, String password) {
        driver.get(PAGE_URL);
        LOGGER.info("try to login");
        LOGGER.debug("set username");
        usernameField.sendKeys(username);
        LOGGER.debug("set password");
        passwordField.sendKeys(password + Keys.ENTER);
    }

    public static String getPageUrl() {
        return PAGE_URL;
    }
}
