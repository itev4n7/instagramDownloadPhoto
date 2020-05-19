package com.instagram.download.photo.dataprovider;

import com.instagram.download.photo.parameters.UserParameters;
import org.testng.annotations.DataProvider;

public class DataProviderClass {

    @DataProvider(name = "testLink1")
    public static Object[][] testLink1() { //yan_lapotkov
        return new Object[][]{{
                  new UserParameters.Builder()
                            .setUsername("test_download_123")
                            .setPassword("TESTdownload123321")
                            .setLink("vkvisionary")
                            .build()}};
    }

    @DataProvider(name = "testLink2")
    public static Object[][] testLink2() {
        return new Object[][]{{
                  new UserParameters.Builder()
                            .setUsername("test_download_123")
                            .setPassword("TESTdownload123321")
                            .setLink("lesha_4n7")
                            .build()}};
    }

    @DataProvider(name = "testLink3")
    public static Object[][] testLink3() {
        return new Object[][]{{
                  new UserParameters.Builder()
                            .setUsername("test_download_123")
                            .setPassword("TESTdownload123321")
                            .setLink("katsalamari")
                            .build()}};
    }

    @DataProvider(name = "testLink4")
    public static Object[][] testLink4() {
        return new Object[][]{{
                  new UserParameters.Builder()
                            .setUsername("test_download_123")
                            .setPassword("TESTdownload123321")
                            .setLink("den.mazur0v")
                            .build()}};
    }

    @DataProvider(name = "testLink5")
    public static Object[][] testLink5() {
        return new Object[][]{{
                  new UserParameters.Builder()
                            .setUsername("test_download_123")
                            .setPassword("TESTdownload123321")
                            .setLink("fucktefive")
                            .build()}};
    }
}
