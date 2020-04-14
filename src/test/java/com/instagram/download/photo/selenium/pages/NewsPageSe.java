package com.instagram.download.photo.selenium.pages;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;

public class NewsPageSe {
    private static Logger logger = Logger.getLogger(NewsPageSe.class);
    private WebDriver driver;

    @FindBy(how = How.XPATH, using = "//*[@placeholder='Search']")
    private WebElement searchField;

    @FindBy(how = How.XPATH, using = "//*[contains(text(),'Not Now')]")
    private WebElement notNowButton;


    public NewsPageSe(WebDriver driver) {
        this.driver = driver;
    }

    public void tryToSearchUser(String link) {
        logger.info("try to search user");
        logger.debug("click Not Now");
        notNowButton.click();
        logger.debug("enter user in search field");
        searchField.sendKeys(link);
        logger.debug("click on user");
        driver.findElement(By.xpath("//*[contains(text(),'" + link + "')]")).click();
    }
}
