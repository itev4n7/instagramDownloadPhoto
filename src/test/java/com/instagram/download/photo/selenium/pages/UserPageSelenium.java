package com.instagram.download.photo.selenium.pages;

import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.util.LinkedHashMap;
import java.util.Map;

public class UserPageSelenium extends BasePageSelenium {
    private static final Logger LOGGER = Logger.getLogger(UserPageSelenium.class);
    private static int count = 1;
    private int step = 250;
    private int postsItems;
    private int savedPostsCount = 0;

    @FindBy(how = How.XPATH, using = "//li[contains(*, 'posts')]/span/span")
    private WebElement postsElement;

    public UserPageSelenium(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
    }

    private int setPostsItems() {
        LOGGER.debug("try set posts items");
        return Integer.parseInt(postsElement.getText());
    }

    public int getPostsItems() {
        return postsItems;
    }

    public int getSavedPostsCount() {
        return savedPostsCount;
    }

    public void downloadUserPhotos() {
        postsItems = setPostsItems();
        LOGGER.info("try to download user photos");
        Map<WebElement, String> savedPhotos = new LinkedHashMap<>();
        LOGGER.debug("put photos");
        while (savedPhotos.size() < postsItems) {
            driver.findElements(By.xpath("//img[@class='FFVAD']")).forEach(webElement -> {
                if (!savedPhotos.containsKey(webElement))
                    savedPhotos.put(webElement, webElement.getAttribute("src"));
            });
            scrollPageDown();
        }
        LOGGER.debug("try to save user photos");
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
            LOGGER.error("Stream didn't create");
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
                LOGGER.error("File didn't write");
                throw new RuntimeException("File didn't write " + e.getMessage());
            }
        }
    }

    private void scrollPageDown() {
        ((JavascriptExecutor) driver).executeScript(String.format("window.scrollBy(0,%d)", step += 550), "");
    }
}
