package com.instagram.download.photo.selenide.tests;

import com.epam.reportportal.testng.ReportPortalTestNGListener;
import com.instagram.download.photo.parameters.UserParameters;
import com.instagram.download.photo.selenide.pages.LoginPage;
import com.instagram.download.photo.selenide.pages.NewsPage;
import com.instagram.download.photo.selenide.pages.UserPage;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;

@Listeners({ReportPortalTestNGListener.class})
public class DownloadUserPhoto extends BaseTest {

    @DataProvider(name = "main")
    public static Object[][] main() {
        return new Object[][]{{new UserParameters("", "", "vkvisionary")}}; //yan_lapotkov
    }

    @Test(dataProvider = "main")
    public void testDownloadUserPhoto(UserParameters user) {
        LoginPage loginPage = new LoginPage();
        loginPage.tryToLogin(user.getUsername(), user.getPassword());

        NewsPage newsPage = new NewsPage();
        newsPage.tryToSearchUser(user.getLink());

        UserPage userPage = new UserPage(db);
        userPage.downloadUserPhotos();

        assertEquals(userPage.getPostItems(), db.getRows());
    }
}