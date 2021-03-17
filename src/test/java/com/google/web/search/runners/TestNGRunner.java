package com.google.web.search.runners;

import com.google.web.search.helper.Screenshot;
import com.google.web.search.pages.DriverFactory;
import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.testng.ITestNGMethod;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;


@CucumberOptions(
        features = "src/test/resources/features",
        glue = {"com.google.web.search.stepdefs"},
        tags = "@BDDTEST-RCSR-12",
        plugin = {"pretty",
   //             "html:target/cucumber-reports/cucumber.html",
   //             "json:target/cucumber-reports/cucumber.json"
        }
)

//@Listeners(ListenersClass.class)
public class TestNGRunner extends AbstractTestNGCucumberTests {
    //   static ExtentTest test;
 //   static ExtentReports extentReports;
    private final Logger logger = LogManager.getLogger( TestNGRunner.class );
    protected static WebDriver driver;

    @BeforeMethod
    public void setUp() {
        logger.info("Setup ...");
  //      extentReports = new ExtentReports();
        DriverFactory.getInstance().setUp();
        driver = DriverFactory.getInstance().getDriver();
    }

    @DataProvider(parallel = true)
    public Object[][] scenario() {
        return super.scenarios();
    }

    @AfterMethod
    public void tearDown(ITestResult result) {
        logger.info("Tear down ...");
        if( ITestResult.FAILURE == result.getStatus()) {
            new Screenshot().screenShot(driver, result);
        }
        driver.quit();
     //   DriverFactory.getInstance().closeDriver();
    }
}
