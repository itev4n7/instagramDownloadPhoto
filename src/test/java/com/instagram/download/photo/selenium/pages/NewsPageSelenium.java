package com.instagram.download.photo.selenium.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;

public class NewsPageSelenium extends BasePageSelenium {

    @FindBy(how = How.XPATH, using = "//*[@placeholder='Search']")
    private WebElement searchField;

    @FindBy(how = How.XPATH, using = "//*[contains(text(),'Not Now')]")
    private WebElement notNowButton;

    public NewsPageSelenium(WebDriver driver) {
        super(driver);
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
