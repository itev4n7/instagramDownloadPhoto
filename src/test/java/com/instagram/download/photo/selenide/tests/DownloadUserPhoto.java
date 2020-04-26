package com.instagram.download.photo.selenide.tests;

import com.epam.reportportal.testng.ReportPortalTestNGListener;
import com.instagram.download.photo.selenide.databases.MariaDB;
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
        return new Object[][]{{"", "", "vkvisionary", "jdbc:mysql://localhost:3307/instagramDownload", "root", "mypass"}}; //yan_lapotkov
    }

    @Test(dataProvider = "main")
    public void testDownloadUserPhoto(String username, String password, String link, String urlDB, String usernameDB, String passwordDB) {
        MariaDB db = new MariaDB(urlDB, usernameDB, passwordDB);

        LoginPage loginPage = new LoginPage();
        loginPage.tryToLogin(username, password);

        NewsPage newsPage = new NewsPage();
        newsPage.tryToSearchUser(link);

        UserPage userPage = new UserPage(db);
        userPage.downloadUserPhotos();

        assertEquals(userPage.getPostItems(), db.getRows());
        db.clearTable();
    }
}