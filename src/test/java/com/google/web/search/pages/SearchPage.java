package com.google.web.search.pages;

import com.google.web.search.helper.ReportPortalScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class SearchPage {
    WebDriver driver;

    @FindBy(name = "q")
    public WebElement searchBox;

    @FindBy(className = "LC20lb")
    public WebElement result;

    public SearchPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    public void setText(String text) {
        searchBox.sendKeys(text);
        screenShot();
    }

    public void clickSubmit() {
        searchBox.submit();
        screenShot();
    }

    public String getResult() {
        screenShot();
        return result.getText();
    }

    private void screenShot() {
       new ReportPortalScreenshot().screenshot(driver);
    }

}
