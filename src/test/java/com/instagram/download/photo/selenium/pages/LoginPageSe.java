package com.instagram.download.photo.selenium.pages;

import org.apache.log4j.Logger;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;

public class LoginPageSe {
    private static Logger logger = Logger.getLogger(LoginPageSe.class);
    private static String pageURL = "https://www.instagram.com/accounts/login/?source=auth_switcher";
    private WebDriver driver;

    @FindBy(how = How.NAME, using = "username")
    private WebElement usernameField;

    @FindBy(how = How.NAME, using = "password")
    private WebElement passwordField;

    public LoginPageSe(WebDriver driver) {
        this.driver = driver;
    }

    public void tryToLogin(String username, String password) {
        driver.get(pageURL);
        logger.info("try to login");
        logger.debug("set username");
        usernameField.sendKeys(username);
        logger.debug("set password");
        passwordField.sendKeys(password + Keys.ENTER);
    }

    public static String getPageURL() {
        return pageURL;
    }
}
