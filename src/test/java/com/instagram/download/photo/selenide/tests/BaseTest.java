package com.instagram.download.photo.selenide.tests;

import com.instagram.download.photo.databases.MariaDB;
import org.apache.log4j.Logger;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class BaseTest {
    private static final Logger LOGGER = Logger.getLogger(BaseTest.class);
    private static final String PATH_TO_PROPERTIES = "src/test/resources/testing.properties";
    MariaDB db = initMariaDB();

    private MariaDB initMariaDB() {
        LOGGER.info("init database");
        try (InputStream input = new FileInputStream(PATH_TO_PROPERTIES);) {
            Properties prop = new Properties();
            prop.load(input);
            LOGGER.debug("try to init database");
            return new MariaDB(
                      prop.getProperty("url"),
                      prop.getProperty("username"),
                      prop.getProperty("password"));
        } catch (IOException e) {
            LOGGER.error("Wrong path to properties");
            throw new RuntimeException("Wrong path to properties" + e.getMessage());
        }
    }

    @BeforeMethod(alwaysRun = true)
    public void setUp() {
        db.initTable();
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        db.dropTable();
    }
}
