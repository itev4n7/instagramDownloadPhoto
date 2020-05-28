package com.instagram.download.photo.selenide.tests;

import com.epam.reportportal.testng.ReportPortalTestNGListener;
import com.instagram.download.photo.annotations.TableName;
import com.instagram.download.photo.databases.MariaDB;
import com.instagram.download.photo.dataprovider.DataProviderClass;
import com.instagram.download.photo.listeners.DataConnectionListener;
import com.instagram.download.photo.listeners.DriverSetUpListener;
import com.instagram.download.photo.listeners.ConcurrentNameListener;
import com.instagram.download.photo.parameters.UserParameters;
import com.instagram.download.photo.selenide.pages.UserPage;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import static com.codeborne.selenide.Selenide.open;
import static org.testng.Assert.assertEquals;

@Listeners({
          ReportPortalTestNGListener.class,
          DriverSetUpListener.class,
          DataConnectionListener.class,
          ConcurrentNameListener.class})
public class TestParallelDownloadUserPhoto {

    @TableName("testLink1")
    @Test(dataProvider = "testLink1", dataProviderClass = DataProviderClass.class)
    public void testDownloadUserPhoto1(UserParameters user) {
        open("https://www.instagram.com/" + user.getLink());

        UserPage userPage = new UserPage();
        userPage.downloadUserPhotos();

        assertEquals(userPage.getPostItems(), MariaDB.getRows());
    }

    @TableName("testLink2")
    @Test(dataProvider = "testLink2", dataProviderClass = DataProviderClass.class)
    public void testDownloadUserPhoto2(UserParameters user) {
        open("https://www.instagram.com/" + user.getLink());

        UserPage userPage = new UserPage();
        userPage.downloadUserPhotos();

        assertEquals(userPage.getPostItems(), MariaDB.getRows());
    }

    @TableName("testLink3")
    @Test(dataProvider = "testLink3", dataProviderClass = DataProviderClass.class)
    public void testDownloadUserPhoto3(UserParameters user) {
        open("https://www.instagram.com/" + user.getLink());

        UserPage userPage = new UserPage();
        userPage.downloadUserPhotos();

        assertEquals(userPage.getPostItems(), MariaDB.getRows());
    }

    @TableName("testLink4")
    @Test(dataProvider = "testLink4", dataProviderClass = DataProviderClass.class)
    public void testDownloadUserPhoto4(UserParameters user) {
        open("https://www.instagram.com/" + user.getLink());

        UserPage userPage = new UserPage();
        userPage.downloadUserPhotos();

        assertEquals(userPage.getPostItems(), MariaDB.getRows());
    }

    @TableName("testLink5")
    @Test(dataProvider = "testLink5", dataProviderClass = DataProviderClass.class)
    public void testDownloadUserPhoto5(UserParameters user) {
        open("https://www.instagram.com/" + user.getLink());

        UserPage userPage = new UserPage();
        userPage.downloadUserPhotos();

        assertEquals(userPage.getPostItems(), MariaDB.getRows());
    }
}