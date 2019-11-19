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
        //find first photo and click
        driver.findElement(By.xpath(
                "//*[@id='react-root']//div[2]/article[2]/div/div[1]/div[1]/div[1]"))
                .click();
        downloadPhoto();

        //click button next photo
        driver.findElement(
                By.xpath("//div[4]/div[1]/div//a")).click();

        // xpath = (//*[@role='button'])[4]//img only fist photo*
        // need find by list            *5* next but only two photo
        // <li have role='button'> to all photo
    }

    private void downloadPhoto() {
        try {
            WebElement elPhoto = driver.findElement(
                    By.xpath("(//*[@role='button'])[4]//img"));
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
