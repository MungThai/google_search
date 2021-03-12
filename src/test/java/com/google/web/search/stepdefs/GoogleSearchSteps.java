package com.google.web.search.stepdefs;

import com.google.web.search.pages.DriverFactory;
import com.google.web.search.pages.SearchPage;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;

public class GoogleSearchSteps {

    private static final Logger log = LogManager.getLogger(GoogleSearchSteps.class);
    private SearchPage searchPage;
    public static WebDriver driver;

    @Given("user launches Google webapp")
    public void openBrowser() {
        driver = DriverFactory.getInstance().getDriver();
        searchPage = new SearchPage(driver);
    }

    @When("user search for a {string}")
    public void searching(String text) {
        searchPage.setText(text);
    }

    @When("click on search button")
    public void clickButton() {
        searchPage.clickSubmit();
    }

    @Then("I expect {string} on top of the list")
    public void verification(String expected) {
        Assert.assertEquals(expected, searchPage.getResult());
    }
}
