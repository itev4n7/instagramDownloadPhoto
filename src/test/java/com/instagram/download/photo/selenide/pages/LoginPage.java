package com.instagram.download.photo.selenide.pages;

import com.codeborne.selenide.Configuration;
import org.apache.log4j.Logger;
import org.openqa.selenium.By;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;

public class LoginPage {
    private static Logger logger = Logger.getLogger(LoginPage.class);

    public void tryTologin(String login, String password) {
        logger.info("try to login");
        Configuration.startMaximized = true;
        open("https://www.instagram.com/accounts/login/?source=auth_switcher");
        $(By.name("username")).setValue(login);
        $(By.name("password")).setValue(password).pressEnter();
        logger.debug("successful login");
    }
}
