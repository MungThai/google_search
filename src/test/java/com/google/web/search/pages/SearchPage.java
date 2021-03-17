package com.google.web.search.pages;

import com.google.web.search.helper.Screenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class SearchPage {
    protected WebDriver driver;

    @FindBy(name = "q")
    private WebElement searchBox;

    @FindBy(className = "LC20lb")
    private WebElement result;

    @FindBy(name = "btnK")
    private WebElement btnOK;

    @FindBy(xpath = "//input[@value='Google Search']")
    private WebElement btnSearch;

    public SearchPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    public void setText(String text) {
        searchBox.sendKeys(text);
    }

    public void clickSubmit() {
        //searchBox.submit();
       // btnOK.click();
        btnSearch.click();
    }

    public String getResult() {
        return result.getText();
    }

}
