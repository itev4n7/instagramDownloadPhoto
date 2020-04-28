package com.instagram.download.photo.selenium.pages;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;

public class NewsPageSelenium extends BasePageSelenium {
    private static final Logger LOGGER = Logger.getLogger(NewsPageSelenium.class);

    @FindBy(how = How.XPATH, using = "//*[@placeholder='Search']")
    private WebElement searchField;

    @FindBy(how = How.XPATH, using = "//*[contains(text(),'Not Now')]")
    private WebElement notNowButton;

    public NewsPageSelenium(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
    }

    public void tryToSearchUser(String link) {
        LOGGER.info("try to search user");
        LOGGER.debug("click Not Now");
        notNowButton.click();
        LOGGER.debug("enter user in search field");
        searchField.sendKeys(link);
        LOGGER.debug("click on user");
        driver.findElement(By.xpath("//*[contains(text(),'" + link + "')]")).click();
    }
}
