package com.instagram.download.photo.selenide.tests;

import com.epam.reportportal.testng.ReportPortalTestNGListener;
import com.instagram.download.photo.databases.MariaDB;
import com.instagram.download.photo.dataprovider.DataProviderClass;
import com.instagram.download.photo.listeners.DatabaseListener;
import com.instagram.download.photo.parameters.UserParameters;
import com.instagram.download.photo.selenide.pages.UserPage;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import static com.codeborne.selenide.Selenide.open;
import static org.testng.Assert.assertEquals;

@Listeners({ReportPortalTestNGListener.class, DatabaseListener.class})
public class TestParallelDownloadUserPhoto {

    @BeforeMethod(alwaysRun = true)
    public void setUp() {
        MariaDB.initTable();
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        MariaDB.dropTable();
    }

    @Test(dataProvider = "testLink1", dataProviderClass = DataProviderClass.class)
    public void testDownloadUserPhoto1(UserParameters user) {
        open("https://www.instagram.com/" + user.getLink());

        UserPage userPage = new UserPage();
        userPage.downloadUserPhotos();

        assertEquals(userPage.getPostItems(), MariaDB.getRows());
    }

    @Test(dataProvider = "testLink2", dataProviderClass = DataProviderClass.class)
    public void testDownloadUserPhoto2(UserParameters user) {
        open("https://www.instagram.com/" + user.getLink());

        UserPage userPage = new UserPage();
        userPage.downloadUserPhotos();

        assertEquals(userPage.getPostItems(), MariaDB.getRows());
    }

    @Test(dataProvider = "testLink3", dataProviderClass = DataProviderClass.class)
    public void testDownloadUserPhoto3(UserParameters user) {
        open("https://www.instagram.com/" + user.getLink());

        UserPage userPage = new UserPage();
        userPage.downloadUserPhotos();

        assertEquals(userPage.getPostItems(), MariaDB.getRows());
    }

    @Test(dataProvider = "testLink4", dataProviderClass = DataProviderClass.class)
    public void testDownloadUserPhoto4(UserParameters user) {
        open("https://www.instagram.com/" + user.getLink());

        UserPage userPage = new UserPage();
        userPage.downloadUserPhotos();

        assertEquals(userPage.getPostItems(), MariaDB.getRows());
    }

    @Test(dataProvider = "testLink5", dataProviderClass = DataProviderClass.class)
    public void testDownloadUserPhoto5(UserParameters user) {
        open("https://www.instagram.com/" + user.getLink());

        UserPage userPage = new UserPage();
        userPage.downloadUserPhotos();

        assertEquals(userPage.getPostItems(), MariaDB.getRows());
    }
}