package com.instagram.download.photo;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.testng.annotations.Test;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

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
        Map<WebElement, Boolean> savedPhoto = new LinkedHashMap(); // <photo, label)>
        Iterator<WebElement> photoPage, labelPage;
        WebElement photo;
        while (savedPhoto.size() < posts) {
            photoPage = driver.findElements(By.xpath("//article//img[@src]")).iterator();
            labelPage = driver.findElements(By.xpath("//article//a[@href]")).iterator();
            while (photoPage.hasNext()) {
                photo = photoPage.next();
                if (!savedPhoto.containsKey(photo)) {
                    try {
                        labelPage.next().findElement(By.tagName("span"));
                        savedPhoto.put(photo, false);
                    } catch (Exception e) {
                        savedPhoto.put(photo, true);
                        downloadPhoto(photo);
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
