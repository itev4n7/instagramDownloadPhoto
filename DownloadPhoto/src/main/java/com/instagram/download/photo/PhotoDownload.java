package com.instagram.download.photo;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.annotations.Test;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.net.URL;

public class PhotoDownload extends InstagramManager {
    private static int countPhoto = 1;

    @Test
    public void testPhotoDownload() throws Exception {
        //create class User link
        String userLink = "vkvisionary";
        goToUserLink(userLink);
        clickFirstPhoto();//have bug

        // button play on video = //span[@aria-label='Play']

        // click following photo
        // //div[4]/div[2]//div[1]//div[2]/button/div

        //click button next post-photo
        // //div[4]/div[1]//a[text()= 'Next']

        // xpath = (//*[@role='button'])[4]//img only fist photo*
        // //div[4]/div[2]//div[1]//div[2]//li[2]//img lists photo

        By nextPostButton = By.xpath("//div[4]/div[1]//a[text()= 'Next']");
        while (isElementPresent(nextPostButton)) {
            if (!isElementPresent(By.xpath("//span[@aria-label='Play']"))) {
                initPhoto();
            }
            driver.findElement(nextPostButton).click();
        }
    }

    private void clickFirstPhoto() {
        driver.findElement(By.xpath(
                "//*[@id='react-root']//div[2]/article[2]/div/div[1]/div[1]/div[1]"))
                .click();
    }

    private void clickNextPhoto() {
        driver.findElement(By.xpath(
                "//div[@class='    coreSpriteRightChevron']")).click();
    }

    private void initPhoto() {
        By nextPhotoButton = By.xpath("//div[@class='    coreSpriteRightChevron']");
        if (isElementPresent(nextPhotoButton)) {
            int countListPage = 1;
            while (isElementPresent(nextPhotoButton)) {
                downloadPhoto(String.format(
                        "//div[4]/div[2]//div[1]//div[2]//li[%d]//img",
                        countListPage++));
                clickNextPhoto();
            }
        } else {
            downloadPhoto("(//*[@role='button'])[4]//img");
        }
    }

    private void downloadPhoto(String locator) {
        try {
            WebElement elPhoto = driver.findElement(
                    By.xpath(locator));
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
