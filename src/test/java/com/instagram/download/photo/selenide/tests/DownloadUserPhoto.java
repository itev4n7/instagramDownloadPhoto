package com.instagram.download.photo.selenide.tests;

import com.epam.reportportal.testng.ReportPortalTestNGListener;
import com.instagram.download.photo.selenide.pages.LoginPage;
import com.instagram.download.photo.selenide.pages.NewsPage;
import com.instagram.download.photo.selenide.pages.UserPage;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;

@Listeners({ReportPortalTestNGListener.class})
public class DownloadUserPhoto {

    @DataProvider(name = "main")
    public static Object[][] main() {
        return new Object[][]{{"", "", "vkvisionary"}}; //yan_lapotkov
    }

    @Test(dataProvider = "main")
    public void testDownloadUserPhoto(String login, String password, String link) {
        LoginPage loginPage = new LoginPage();
        loginPage.tryTologin(login, password);

        NewsPage newsPage = new NewsPage();
        newsPage.tryToSearchUser(link);

        UserPage userPage = new UserPage();
        userPage.downloadUserPhotos();

        assertEquals(userPage.getSavedPostsCount(), userPage.getPostsItems());
    }
}
