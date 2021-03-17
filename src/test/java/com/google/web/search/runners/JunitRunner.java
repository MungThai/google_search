package com.google.web.search.runners;

import com.google.web.search.helper.Screenshot;
import com.google.web.search.pages.DriverFactory;
import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.openqa.selenium.WebDriver;
import org.testng.ITestResult;

@RunWith(Cucumber.class)
@CucumberOptions(
        features = "src/test/resources/features/GoogleSearch.feature",
        glue = {"com.google.web.search.stepdefs"},
       // tags = "@Sanity",
        plugin = {"pretty",
                "html:target/cucumber-reports/cucumber.html",
                "junit:target/cucumber-reports/cucumber.xml"
        }
)

public class JunitRunner {
    private static final Logger logger = LogManager.getLogger( JunitRunner.class );
    protected static WebDriver driver;

    @BeforeClass
    public static void setUp() {
        logger.info("Setup ...");
        DriverFactory.getInstance().setUp();
        driver = DriverFactory.getInstance().getDriver();
        logger.info("Initialize driver");
    }


    @Parameterized.AfterParam
    public static void tearDown(ITestResult result) throws Exception
    {
        logger.info("Tear down ...");
        if( ITestResult.FAILURE == result.getStatus()) {
//           new Screenshot().screenShot(result, driver);
        }
        DriverFactory.getInstance().closeDriver();
        logger.info("Close driver");
    }
}
