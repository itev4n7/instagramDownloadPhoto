package com.instagram.download.photo.selenide.pages;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;

import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.$;

public class NewsPage {
    private static final Logger LOGGER = Logger.getLogger(NewsPage.class);

    public void tryToSearchUser(String link) {
        LOGGER.info("try to search user");
        LOGGER.debug("click Not Now");
        $(byText("Not Now")).click();
        LOGGER.debug("enter user in search field");
        $(By.xpath("//*[@placeholder='Search']")).setValue(link);
        LOGGER.debug("click on user");
        $(byText(link)).click();
    }
}
