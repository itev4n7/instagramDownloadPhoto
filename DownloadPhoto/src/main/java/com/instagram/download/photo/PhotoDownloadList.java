package com.instagram.download.photo;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.testng.annotations.Test;

import java.util.LinkedList;
import java.util.List;

public class PhotoDownloadList extends InstagramManager {
    private static int posts;

    @Test
    public void testPhotoDownload() throws Exception {
        String userLink = "vkvisionary";
        goToUserLink(userLink);
        initPosts();
        downloadSinglePhoto();
    }

    private void initPosts() {
        posts = Integer.parseInt(driver.findElement(
                By.xpath("(//li[1]//span)[last()]")).getText());
    }

    /*
     * have bug - need to change the logic of loading and checking
     * cause - dynamic div(row of 3 posts), maximum number per page 20
     */
    private void downloadSinglePhoto() throws InterruptedException {
        List<WebElement> photoOnPage = new LinkedList<>();
        for (int count = 1; count <= posts; count++) {
            if (count % 3 == 0) {
                scrollPageDown();
            }
            if (!isLabelPost(count)) {
                photoOnPage.add(driver.findElement(
                        By.xpath("(//article//img[@src])[" + count + "]")));
            }
        }
        for (WebElement webElement : photoOnPage) {
            downloadPhoto(webElement);
        }

    }

    private void scrollPageDown() {
        JavascriptExecutor jse = (JavascriptExecutor) driver;
        jse.executeScript("window.scrollBy(0,250)", "");
    }

    private boolean isLabelPost(int count) {
        return isElementPresent(By.xpath(
                "(//article//img[@src])[" + count + "]//ancestor::a//span"));
    }

    private String getPhotoAttribute(int count) {
        String photoAttribute = driver.findElement(By.xpath(
                "(//article//img[@src])[" + count + "]//ancestor::a//span"))
                .getAttribute("aria-label");
        return photoAttribute;
    }

    private boolean isCarousel(int count) {
        if (isLabelPost(count)) {
            return getPhotoAttribute(count).equals("Carousel");
        }
        return false;
    }

    private boolean isVideo(int count) {
        if (isLabelPost(count)) {
            return getPhotoAttribute(count).equals("Video");
        }
        return false;
    }
}
