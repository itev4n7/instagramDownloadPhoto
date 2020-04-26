package com.instagram.download.photo.selenide.pages;

import com.instagram.download.photo.selenide.databases.MariaDB;
import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.LinkedHashMap;
import java.util.Map;

import static com.codeborne.selenide.Selenide.*;

public class UserPage {
    private static final Logger logger = Logger.getLogger(UserPage.class);
    private static int count = 1;
    private int step = 250;
    private final int postItems = setPostsItems();
    private final MariaDB db;

    public UserPage(MariaDB db) {
        this.db = db;
    }

    private int setPostsItems() {
        logger.debug("try set posts items");
        return Integer.parseInt($(By.xpath("//li[contains(*, 'posts')]/span/span")).getText());
    }

    public int getPostItems() {
        return postItems;
    }

    public void downloadUserPhotos() {
        logger.info("try to download user photos");
        Map<WebElement, String> savedPhotos = new LinkedHashMap<>();
        logger.debug("put photos");
        while (savedPhotos.size() < postItems) {
            $$(By.xpath("//img[@class='FFVAD']")).forEach(webElement -> {
                if (!savedPhotos.containsKey(webElement))
                    savedPhotos.put(webElement, webElement.getAttribute("src"));
            });
            executeJavaScript(String.format("window.scrollBy(0,%d)", step += 550));
        }
        logger.debug("try to save user photos");
        downloadSaved(savedPhotos);
    }

    private void downloadSaved(Map<WebElement, String> savedPhotos) {
        savedPhotos.values()
                  .stream()
                  .map(this::transformURL)
                  .forEach(this::writePhotoToDB);
    }

    private InputStream transformURL(String src) {
        try {
            return new URL(src).openStream();
        } catch (IOException e) {
            logger.error("Stream didn't create");
            throw new RuntimeException("Stream didn't create " + e.getMessage());
        }
    }

    private void writePhotoToDB(InputStream inputStream) {
        if (inputStream != null) {
            db.writeBlob(count++, inputStream);
        }
    }
}
