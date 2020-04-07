package com.instagram.download.photo;

import org.apache.commons.io.IOUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.annotations.Test;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.util.LinkedHashMap;
import java.util.Map;

import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.*;

public class PhotoDownloadSelenide {
    private static Integer STEP = 250;
    private static int count = 1;

    @Test
    public void testPhotoDownload() {
        String login = "";
        String password = "";
        String link = "vkvisionary"; //yan_lapotkov

        tryTologin(login, password);
        tryToSearchUser(link);
        downloadUserPhotos(getPostsItems());
    }

    private void tryToSearchUser(String link) {
        $(byText("Not Now")).click();
        $(By.xpath("//*[@placeholder='Search']")).setValue(link);
        $(byText(link)).click();
    }

    private void tryTologin(String login, String password) {
        open("https://www.instagram.com/accounts/login/?source=auth_switcher");
        $(By.name("username")).setValue(login);
        $(By.name("password")).setValue(password).pressEnter();
    }

    private int getPostsItems() {
        return Integer.parseInt($(By.xpath("//li[contains(*, 'posts')]/span/span")).getText());
    }

    private void downloadUserPhotos(int posts) {
        Map<WebElement, String> savedPhotos = new LinkedHashMap<>();
        while (savedPhotos.size() < posts) {
            $$(By.xpath("//img[@class='FFVAD']")).forEach(webElement -> {
                if (!savedPhotos.containsKey(webElement))
                    savedPhotos.put(webElement, webElement.getAttribute("src"));
            });
            executeJavaScript(String.format("window.scrollBy(0,%d)", STEP += 550));
        }
        downloadSaved(savedPhotos);
    }

    private void downloadSaved(Map<WebElement, String> savedPhotos) {
        savedPhotos.values()
                .stream()
                .map(this::transformURL)
                .forEach(this::downloadPhoto);
    }

    InputStream transformURL(String src) {
        try {
            return new URL(src).openStream();
        } catch (IOException e) {
            throw new RuntimeException("Stream didn't create " + e.getMessage());
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
}
