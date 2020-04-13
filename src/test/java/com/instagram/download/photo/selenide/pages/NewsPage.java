package com.instagram.download.photo.selenide.pages;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;

import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.$;

public class NewsPage {
    private static Logger logger = Logger.getLogger(NewsPage.class);

    public void tryToSearchUser(String link) {
        logger.info("try to search user");
        logger.debug("click Not Now");
        $(byText("Not Now")).click();
        logger.debug("enter user in search field");
        $(By.xpath("//*[@placeholder='Search']")).setValue(link);
        logger.debug("click on user");
        $(byText(link)).click();
    }
}
