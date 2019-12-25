package com.instagram.download.photo;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.net.URL;
import java.util.concurrent.TimeUnit;

import static org.testng.Assert.fail;

public class InstagramManager {

    private static int countPhoto = 1;
    protected WebDriver driver;
    private boolean acceptNextAlert = true;
    private StringBuffer verificationErrors = new StringBuffer();

    @BeforeClass(alwaysRun = true)
    public void setUp() throws Exception {
        WebDriverManager.chromedriver().setup();
        start();
    }

    @AfterClass(alwaysRun = true)
    public void tearDown() throws Exception {
        stop();
    }

    private void start() {
        driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(1, TimeUnit.SECONDS);
    }

    private void stop() {
        driver.quit();
        String verificationErrorString = verificationErrors.toString();
        if (!"".equals(verificationErrorString)) {
            fail(verificationErrorString);
        }
    }

    public boolean isElementPresent(By by) {
        try {
            driver.findElement(by);
            return true;
        } catch (NoSuchElementException e) {
            return false;
        }
    }

    public boolean isAlertPresent() {
        try {
            driver.switchTo().alert();
            return true;
        } catch (NoAlertPresentException e) {
            return false;
        }
    }

    public String closeAlertAndGetItsText() {
        try {
            Alert alert = driver.switchTo().alert();
            String alertText = alert.getText();
            if (acceptNextAlert) {
                alert.accept();
            } else {
                alert.dismiss();
            }
            return alertText;
        } finally {
            acceptNextAlert = true;
        }
    }

    public void goToUserLink(String userLink) {
        driver.get("https://www.instagram.com/" + userLink);
    }

    public void clickByXpath(By locator) {
        driver.findElement(locator).click();
    }

    public void clickByXpath(String locator) {
        driver.findElement(By.xpath(locator)).click();
    }

    public void downloadPhoto(WebElement photo) {
        try {
            String srcPhoto = photo.getAttribute("src");
            URL imageURL = new URL(srcPhoto);
            BufferedImage saveImage = ImageIO.read(imageURL);
            ImageIO.write(saveImage, "png",
                    new File(String.format("photo%d.png", countPhoto++)));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void loginToInst(String login, String password, String link) throws InterruptedException {
        driver.get("https://www.instagram.com/accounts/login/?source=auth_switcher");
        driver.findElement(By.name("username")).click();
        driver.findElement(By.name("username")).sendKeys(login);
        driver.findElement(By.name("password")).click();
        driver.findElement(By.name("password")).sendKeys(password);
        driver.findElement(By.xpath("//article//div[4]/button")).click();
        Thread.sleep(2000);
        driver.findElement(By.xpath("//div[3]/button[2]")).click();
        driver.findElement(By.xpath("//section//div[2]/div/div/div[2]")).click();
        driver.findElement(By.xpath("//nav//input")).sendKeys("@" + link);
        driver.findElement(By.xpath("//nav//a[1]//div[2]/div/span")).click();
        Thread.sleep(2000);
    }
}
