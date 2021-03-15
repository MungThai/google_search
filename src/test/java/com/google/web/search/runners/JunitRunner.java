package com.google.web.search.runners;

import com.google.web.search.pages.DriverFactory;
import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        features = "src/test/resources/features/GoogleSearch.feature",
        glue = {"com.google.web.search.stepdefs"},
       // tags = "@Sanity",
        plugin = {"pretty",
                "html:target/cucumber-reports/cucumber.html",
                "json:target/cucumber-reports/cucumber.json"
        }
)

public class JunitRunner {
    private static final Logger log = LogManager.getLogger( JunitRunner.class );

    @BeforeClass
    public static void setUp() {
        log.info("Setup ...");
        DriverFactory.getInstance().setUp();
        log.info("Initialize driver");
    }


    @AfterClass
    public static void tearDown() throws Exception
    {

        log.info("Tear down ...");
        DriverFactory.getInstance().closeDriver();
        log.info("Close driver");
    }
}
