package com.instagram.download.photo;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.testng.annotations.Test;

import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

public class PhotoDownloadList extends InstagramManager {
    private static int posts;

    @Test
    public void testPhotoDownload() throws Exception {
        String login = "";
        String password = "";
        String link = "yan_lapotkov"; //vkvisionary
        loginToInst(login, password, link);
        initPosts();
        downloadSinglePhoto();
    }

    private void initPosts() {
        posts = Integer.parseInt(driver.findElement(
                By.xpath("(//li[1]//span)[last()]")).getText());
    }


    private void downloadSinglePhoto() {
        Map<WebElement, Boolean> checkPhoto = new LinkedHashMap(); //<photo, label)>
        Set<WebElement> allElements = new LinkedHashSet<>(); //link on label
        while (checkPhoto.size() < posts) { //fin ver --> (size < posts)
            allElements.addAll(driver.findElements(By.xpath("//article//a[@href]")));
            boolean single;
            for (WebElement el : allElements) {
                try {
                    el.findElement(By.tagName("span"));
                    single = false;
                } catch (Exception e) {
                    single = true;
                }
                el = el.findElement(By.tagName("img"));
                if (!checkPhoto.containsKey(el)) {
                    checkPhoto.put(el, single);
                    if (checkPhoto.get(el)) {
                        downloadPhoto(el);
                    }
                }
            }
            for (int i = 0; i < 6; i++) {
                scrollPageDown();
            }
        }
    }

    private void scrollPageDown() {
        JavascriptExecutor jse = (JavascriptExecutor) driver;
        jse.executeScript("window.scrollBy(0,250)", "");
    }

}
