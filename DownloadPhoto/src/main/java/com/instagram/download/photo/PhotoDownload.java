package com.instagram.download.photo;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.annotations.Test;

import java.util.List;

public class PhotoDownload extends InstagramManager {
    private static int clickByPhoto = 2;

    @Test
    public void testPhotoDownload() throws Exception {
        String userLink = "lesha_4n7";
        goToUserLink(userLink);
        checkPanels();
        clickFirstPhoto();
        uploadPhotos();
    }

    private void uploadPhotos() {
        By nextPostButton = By.xpath("//div[4]/div[1]//a[text()= 'Next']");
        while (true) {
            if (isVideo()) {
                initPhoto();
            }
            if (isElementPresent(nextPostButton)) {
                clickByXpath(nextPostButton);
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
        clickByXpath("(//*[@id='react-root']//div[" + clickByPhoto + "]//a/div)[1]");
    }

    private void clickNextPhoto() {
        clickByXpath("//div[@class='    coreSpriteRightChevron']");
    }

    private void initPhoto() {
        By nextPhotoButton = By.xpath("//div[@class='    coreSpriteRightChevron']");
        if (isElementPresent(nextPhotoButton)) {
            int countListPage = 1;
            downloadPhoto(driver.findElement(
                    By.xpath(String.format(
                            "//article//li[%d]//img[@srcset]",
                            countListPage++))));
            while (isElementPresent(nextPhotoButton)) {
                clickNextPhoto();
                downloadPhoto(driver.findElement(
                        By.xpath(String.format(
                                "//article//li[%d]//img[@srcset]",
                                countListPage++))));
            }
        } else {
            downloadPhoto(driver.findElement(
                    By.xpath("//*[@role='button']//img[@srcset]")));
        }
    }
}
