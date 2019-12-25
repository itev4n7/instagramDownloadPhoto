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
        //String userLink = "yan_lapotkov"; //vkvisionary
        //goToUserLink(userLink);
        loginToInst();
        initPosts();
        //downloadSinglePhoto();
        downloadPackPosts();
    }

    private void loginToInst() throws InterruptedException {
        driver.get("https://www.instagram.com/accounts/login/?source=auth_switcher");
        driver.findElement(By.name("username")).click();
        driver.findElement(By.name("username")).clear();
        driver.findElement(By.name("username")).sendKeys("your log");
        driver.findElement(By.name("password")).click();
        driver.findElement(By.name("password")).clear();
        driver.findElement(By.name("password")).sendKeys("your pass");
        driver.findElement(By.xpath("//article//div[4]/button")).click();
        Thread.sleep(2000);
        driver.findElement(By.xpath("//div[3]/button[2]")).click();
        driver.findElement(By.xpath("//section//div[2]/div/div/div[2]")).click();
        driver.findElement(By.xpath("//nav//input")).sendKeys("@yan_laputkov");
        driver.findElement(By.xpath("//nav//a[1]//div[2]/div/span")).click();
        Thread.sleep(2000);
    }

    private void initPosts() {
        posts = Integer.parseInt(driver.findElement(
                By.xpath("(//li[1]//span)[last()]")).getText());
    }


    private void downloadPackPosts() {
        Map<WebElement, Boolean> srcp = new LinkedHashMap(); //<photo, label)>
        Set<WebElement> ssr = new LinkedHashSet<>(); //link on photo
        Set<WebElement> lab = new LinkedHashSet<>(); //link on label
        while (srcp.size() < 50) { //fin ver --> (ssr < posts)
            ssr.addAll(driver.findElements(By.xpath("//article//img[@src]")));
            lab.addAll(driver.findElements(By.xpath("//article//a[@href]")));
            for (WebElement el : ssr) {
                if (!srcp.containsKey(el)) {
                    srcp.put(el, func());
                    if (srcp.get(el)) {
                        downloadPhoto(el);
                    }
                }
            }
            System.out.println(srcp.size());
            for (int i = 0; i < 6; i++) {
                scrollPageDown();
            }
        }
        //  //article//a[@href]

    }

    private Boolean func() {
        //need to create check on label with lab
        return true;
    }

    /*
     * have bug - need to change the logic of loading and checking
     * cause - dynamic div(row of 3 posts), maximum number per page 20
     */

    private void downloadSinglePhoto() throws InterruptedException {
        Set<WebElement> photoOnPage = new LinkedHashSet<>();
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

    private void scrollPageUp() {
        JavascriptExecutor jse = (JavascriptExecutor) driver;
        jse.executeScript("window.scrollBy(0,-250)", "");
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
