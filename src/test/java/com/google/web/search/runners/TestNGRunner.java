package com.google.web.search.runners;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.google.web.search.pages.DriverFactory;
import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;


@CucumberOptions(
        features = "src/test/resources/features/GoogleSearch.feature",
        glue = {"com.google.web.search.stepdefs"},
        tags = "@Sanity",
        plugin = {"pretty",
                "html:target/cucumber-reports/cucumber.html",
                "json:target/cucumber-reports/cucumber.json"
        }
)

//@Listeners(ListenersClass.class)
public class TestNGRunner extends AbstractTestNGCucumberTests {
    static ExtentTest test;
    static ExtentReports extentReports;


    private static final Logger log = LogManager.getLogger( TestNGRunner.class );

    @BeforeMethod
    public void setUp() {
        log.info("Setup ...");
        extentReports = new ExtentReports();
        DriverFactory.getInstance().setUp();
        log.info("Initialize driver");
    }

    @DataProvider(parallel = true)
    public Object[][] scenario() {
        return super.scenarios();
    }

    @AfterMethod
    public static void tearDown(ITestResult result) {
        log.info("Tear down ...");
        DriverFactory.getInstance().closeDriver();
        log.info("Close driver");
    }
}
