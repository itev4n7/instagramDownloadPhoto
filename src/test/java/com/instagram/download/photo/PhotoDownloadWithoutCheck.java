package com.instagram.download.photo;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.testng.annotations.Test;

import java.util.LinkedHashMap;
import java.util.Map;

public class PhotoDownloadWithoutCheck extends InstagramManager {
    private static Integer STEP = 250;
    private int posts;

    @Test
    public void testPhotoDownload() throws Exception {
        String login = "";
        String password = "";
        String link = "vkvisionary"; //yan_lapotkov
        loginToInst(login, password, link);
        posts = getPostItems();
        downloadPhotos();
    }

    private void downloadPhotos() {
        Map<WebElement, String> savedPhotos = new LinkedHashMap<>();
        while (savedPhotos.size() < posts) {
            driver.findElements(By.xpath("//img[@class='FFVAD']"))
                    .forEach(webElement -> {
                        if (!savedPhotos.containsKey(webElement))
                            savedPhotos.put(webElement, webElement.getAttribute("src"));
                    });
            scrollPageDown();
        }
        downloadSaved(savedPhotos);
    }

    private void downloadSaved(Map<WebElement, String> savedPhotos) {
        savedPhotos.values()
                .stream()
                .map(this::transformURL)
                .forEach(this::downloadPhoto);
    }

    private void scrollPageDown() {
        JavascriptExecutor jse = (JavascriptExecutor) driver;
        jse.executeScript(String.format("window.scrollBy(0,%d)", STEP += 550), "");
    }
}