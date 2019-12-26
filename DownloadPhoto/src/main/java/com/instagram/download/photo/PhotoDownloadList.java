package com.instagram.download.photo;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.testng.annotations.Test;

import java.util.*;

public class PhotoDownloadList extends InstagramManager {
    private static int posts;

    @Test
    public void testPhotoDownload() throws Exception {
        String login = "";
        String password = "";
        String link = "vkvisionary"; //yan_lapotkov
        loginToInst(login, password, link);
        initPosts();
        downloadSinglePhoto();
    }

    private void initPosts() {
        posts = Integer.parseInt(driver.findElement(
                By.xpath("(//li[1]//span)[last()]")).getText());
    }


    private void downloadSinglePhoto() {
        Map<WebElement, Boolean> srcp = new LinkedHashMap(); //<photo, label)>
        Set<WebElement> ssr = new LinkedHashSet<>(); //link on photo
        Set<WebElement> lab = new LinkedHashSet<>(); //link on label
        Iterator<WebElement> iterPhoto, iterLabel;
        WebElement photo;
        while (ssr.size() < posts) { //fin ver --> (ssr < posts)
            ssr.addAll(driver.findElements(By.xpath("//article//img[@src]")));
            lab.addAll(driver.findElements(By.xpath("//article//a[@href]")));
            iterPhoto = ssr.iterator();
            iterLabel = lab.iterator();
            while (iterPhoto.hasNext()) {
                photo = iterPhoto.next();
                if (!srcp.containsKey(photo)) {
                    try {
                        iterLabel.next().findElement(By.tagName("span"));
                        srcp.put(photo, false);
                    } catch (Exception e) {
                        srcp.put(photo, true);
                        downloadPhoto(photo);
                    }
                }
            }
            System.out.println(srcp.size());
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
