package com.instagram.download.photo.selenium.tests;

import com.epam.reportportal.testng.ReportPortalTestNGListener;
import com.instagram.download.photo.selenium.pages.LoginPageSe;
import com.instagram.download.photo.selenium.pages.NewsPageSe;
import com.instagram.download.photo.selenium.pages.UserPageSe;
import org.openqa.selenium.support.PageFactory;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;

@Listeners({ReportPortalTestNGListener.class})
public class DownloadUserPhotoSe extends BaseTest {

    @DataProvider(name = "main")
    public static Object[][] main() {
        return new Object[][]{{"", "", "vkvisionary"}}; //yan_lapotkov
    }

    @Test(dataProvider = "main")
    public void testDownloadUserPhotoSe(String username, String password, String link) {
        LoginPageSe loginPageSe = PageFactory.initElements(driver, LoginPageSe.class);
        loginPageSe.tryToLogin(username, password);

        NewsPageSe newsPageSe = PageFactory.initElements(driver, NewsPageSe.class);
        newsPageSe.tryToSearchUser(link);

        UserPageSe userPageSe = PageFactory.initElements(driver, UserPageSe.class);
        userPageSe.downloadUserPhotos();

        assertEquals(userPageSe.getSavedPostsCount(), userPageSe.getPostsItems());
    }
}
