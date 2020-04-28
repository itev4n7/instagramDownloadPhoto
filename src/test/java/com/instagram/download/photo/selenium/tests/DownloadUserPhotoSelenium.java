package com.instagram.download.photo.selenium.tests;

import com.epam.reportportal.testng.ReportPortalTestNGListener;
import com.instagram.download.photo.selenium.pages.LoginPageSelenium;
import com.instagram.download.photo.selenium.pages.NewsPageSelenium;
import com.instagram.download.photo.selenium.pages.UserPageSelenium;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;

@Listeners({ReportPortalTestNGListener.class})
public class DownloadUserPhotoSelenium extends BaseTestSelenium {

    @DataProvider(name = "main")
    public static Object[][] main() {
        return new Object[][]{{"", "", "vkvisionary"}}; //yan_lapotkov
    }

    @Test(dataProvider = "main")
    public void testDownloadUserPhotoSe(String username, String password, String link) {
        LoginPageSelenium loginPageSe = new LoginPageSelenium(driver);
        loginPageSe.tryToLogin(username, password);

        NewsPageSelenium newsPageSe = new NewsPageSelenium(driver);
        newsPageSe.tryToSearchUser(link);

        UserPageSelenium userPageSe = new UserPageSelenium(driver);
        userPageSe.downloadUserPhotos();

        assertEquals(userPageSe.getSavedPostsCount(), userPageSe.getPostsItems());
    }
}
