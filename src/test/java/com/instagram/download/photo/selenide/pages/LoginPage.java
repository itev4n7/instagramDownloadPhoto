package com.instagram.download.photo.selenide.pages;

import com.codeborne.selenide.WebDriverRunner;
import org.apache.log4j.Logger;
import org.openqa.selenium.By;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;

public class LoginPage {
    private static final Logger LOGGER = Logger.getLogger(LoginPage.class);
    private static final String loginUrl = "https://www.instagram.com/accounts/login/?source=auth_switcher";
    private static boolean isSingIn;

    public void tryToLogin(String username, String password) {
        open(loginUrl);
        if (WebDriverRunner.url().equals(loginUrl)) {
            LOGGER.info("try to login");
            LOGGER.debug("set username");
            $(By.name("username")).setValue(username);
            LOGGER.debug("set password");
            $(By.name("password")).setValue(password).pressEnter();
            isSingIn = true;
        } else {
            isSingIn = false;
        }
    }

    public static boolean isSingIn() {
        return isSingIn;
    }
}
