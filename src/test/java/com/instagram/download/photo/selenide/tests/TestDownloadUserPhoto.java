package com.instagram.download.photo.selenide.tests;

import com.epam.reportportal.testng.ReportPortalTestNGListener;
import com.instagram.download.photo.databases.MariaDB;
import com.instagram.download.photo.dataprovider.DataProviderClass;
import com.instagram.download.photo.listeners.DriverSetUpListener;
import com.instagram.download.photo.parameters.UserParameters;
import com.instagram.download.photo.selenide.pages.LoginPage;
import com.instagram.download.photo.selenide.pages.NewsPage;
import com.instagram.download.photo.selenide.pages.UserPage;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;

@Listeners({ReportPortalTestNGListener.class, DriverSetUpListener.class})
public class TestDownloadUserPhoto {

    @Test(dataProvider = "testLink1", dataProviderClass = DataProviderClass.class, enabled = true)
    public void testDownloadUserPhoto(UserParameters user) {
        LoginPage loginPage = new LoginPage();
        loginPage.tryToLogin(user.getUsername(), user.getPassword());

        NewsPage newsPage = new NewsPage();
        newsPage.tryToSearchUser(user.getLink());

        UserPage userPage = new UserPage();
        userPage.downloadUserPhotos();

        assertEquals(userPage.getPostItems(), MariaDB.getRows());
    }
}
