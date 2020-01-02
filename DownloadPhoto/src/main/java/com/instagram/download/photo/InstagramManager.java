package com.instagram.download.photo;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.apache.commons.io.IOUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.URL;
import java.util.concurrent.TimeUnit;

public class InstagramManager {

    private static int count = 1;
    static WebDriver driver;
    private boolean acceptNextAlert = true;

    @BeforeClass(alwaysRun = true)
    public void setUp() {
        WebDriverManager.chromedriver().setup();
        start();
    }

    @AfterClass(alwaysRun = true)
    public void tearDown() {
        driver.quit();
    }

    private void start() {
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
    }

    boolean isElementPresent(By by) {
        try {
            driver.findElement(by);
            return true;
        } catch (NoSuchElementException e) {
            return false;
        }
    }

    boolean isAlertPresent() {
        try {
            driver.switchTo().alert();
            return true;
        } catch (NoAlertPresentException e) {
            return false;
        }
    }

    String closeAlertAndGetItsText() {
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

    void goToUserLink(String userLink) {
        driver.get("https://www.instagram.com/" + userLink);
    }

    void downloadPhoto(String srcPhoto) {
        try {
            URL imageURL = new URL(srcPhoto);
            BufferedImage saveImage = ImageIO.read(imageURL);
            ImageIO.write(saveImage, "png",
                    new File(String.format("photo%d.png", count++)));
        } catch (Exception e) {
            throw new RuntimeException("File didn't write " + e.getMessage());
        }
    }

    void downloadPhoto(InputStream inputStream) {
        if (inputStream != null) {
            try (OutputStream fileOutputStream = new FileOutputStream(ClassLoader.getSystemResource(".").getPath()
                    + String.format("/photo%d.png", count++))) {
                IOUtils.copy(inputStream, fileOutputStream);
                inputStream.close();
            } catch (IOException e) {
                throw new RuntimeException("File didn't write " + e.getMessage());
            }
        }
    }

    static int getPostItems() {
        return Integer.parseInt(driver.findElement(
                By.xpath("//li[contains(*, 'posts')]/span/span")).getText());
    }

    void loginToInst(String login, String password, String link) throws InterruptedException {
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

    InputStream transformURL(String src) {
        try {
            return new URL(src).openStream();
        } catch (IOException e) {
            throw new RuntimeException("Stream didn't create " + e.getMessage());
        }
    }
}
