package com.instagram.download.photo;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.annotations.Test;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.net.URL;
import java.util.List;

public class PhotoDownload extends InstagramManager {
    private static int countPhoto = 1;
    private static int clickByPhoto = 2;

    @Test
    public void testPhotoDownload() throws Exception {
        //create class User link
        String userLink = "katsalamari";
        goToUserLink(userLink);

        checkPanels();

        clickFirstPhoto();

        // button play on video = //span[@aria-label='Play']

        // click following photo
        // //div[4]/div[2]//div[1]//div[2]/button/div

        //click button next post-photo
        // //div[4]/div[1]//a[text()= 'Next']

        // xpath = (//*[@role='button'])[4]//img only fist photo*
        // //div[4]/div[2]//div[1]//div[2]//li[2]//img lists photo

        //       (//*[@id='react-root']//div[3]//a/div)[1] if have 3 div on page
        //  //child::main/child::div/child::div and get arr if size 2 use div[2] else div[3]

        By nextPostButton = By.xpath("//div[4]/div[1]//a[text()= 'Next']");

        while (true) {
            if (isVideo()) {
                initPhoto();
            }
            if (isElementPresent(nextPostButton)) {
                driver.findElement(nextPostButton).click();
            } else {
                break;
            }
        }
    }

    private boolean isVideo() {
        return !isElementPresent(By.xpath("//span[@aria-label='Play']"));
    }

    /*
     * Check correct id to locator for clickFirstPhoto
     */
    private void checkPanels() {
        List<WebElement> arr = driver.findElements(
                By.xpath("//child::main/child::div/child::div"));
        if (arr.size() == 2) {
            clickByPhoto = 2;
        } else if (arr.size() == 3) {
            clickByPhoto = 3;
        }
    }

    private void clickFirstPhoto() {
        driver.findElement(By.xpath(
                "(//*[@id='react-root']//div[" + clickByPhoto + "]//a/div)[1]")) //click
                .click();
    }

    private void clickNextPhoto() {
        driver.findElement(By.xpath(
                "//div[@class='    coreSpriteRightChevron']")).click(); //click
    }

    private void initPhoto() {
        By nextPhotoButton = By.xpath("//div[@class='    coreSpriteRightChevron']");
        if (isElementPresent(nextPhotoButton)) {
            int countListPage = 1;
            downloadPhoto(String.format(
                    "//article//li[%d]//img[@srcset]",
                    countListPage++));
            while (isElementPresent(nextPhotoButton)) {
                clickNextPhoto();
                downloadPhoto(String.format(
                        "//article//li[%d]//img[@srcset]",
                        countListPage++));
            }
        } else {
            downloadPhoto("//*[@role='button']//img[@srcset]");
        }
    }

    private void downloadPhoto(String locator) {
        try {
            WebElement elPhoto = driver.findElement(By.xpath(locator));
            String srcPhoto = elPhoto.getAttribute("src");
            URL imageURL = new URL(srcPhoto);
            BufferedImage saveImage = ImageIO.read(imageURL);
            ImageIO.write(saveImage, "png",
                    new File(String.format("photo%d.png", countPhoto++)));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
