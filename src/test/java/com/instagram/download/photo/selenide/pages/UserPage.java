package com.instagram.download.photo.selenide.pages;

import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.util.LinkedHashMap;
import java.util.Map;

import static com.codeborne.selenide.Selenide.*;

public class UserPage {
    private static Logger logger = Logger.getLogger(UserPage.class);
    private static int count = 1;
    private int step = 250;
    private int postsItems = setPostsItems();
    private int savedPostsCount = 0;

    private int setPostsItems() {
        logger.debug("try set posts items");
        return Integer.parseInt($(By.xpath("//li[contains(*, 'posts')]/span/span")).getText());
    }

    public int getPostsItems() {
        return postsItems;
    }

    public int getSavedPostsCount() {
        return savedPostsCount;
    }

    public void downloadUserPhotos() {
        logger.info("try to download user photos");
        Map<WebElement, String> savedPhotos = new LinkedHashMap<>();
        logger.debug("put photos");
        while (savedPhotos.size() < postsItems) {
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
                .forEach(this::downloadPhoto);
    }

    private InputStream transformURL(String src) {
        try {
            return new URL(src).openStream();
        } catch (IOException e) {
            logger.error("Stream didn't create");
            throw new RuntimeException("Stream didn't create " + e.getMessage());
        }
    }

    private void downloadPhoto(InputStream inputStream) {
        if (inputStream != null) {
            try (OutputStream fileOutputStream = new FileOutputStream(ClassLoader.getSystemResource(".").getPath()
                    + String.format("/photo%d.png", count++))) {
                IOUtils.copy(inputStream, fileOutputStream);
                inputStream.close();
                savedPostsCount++;
            } catch (IOException e) {
                logger.error("File didn't write");
                throw new RuntimeException("File didn't write " + e.getMessage());
            }
        }
    }
}
