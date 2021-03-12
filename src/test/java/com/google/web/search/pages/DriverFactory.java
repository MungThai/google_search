package com.google.web.search.pages;

import com.google.web.search.utils.Utilities;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;

import java.util.Locale;
import java.util.concurrent.TimeUnit;

public class DriverFactory {
    int TIME_OUT = 10;
    Utilities utils = new Utilities();

    private static final DriverFactory instance = new DriverFactory();

    ThreadLocal<WebDriver> driver = new ThreadLocal<WebDriver>();
    private WebDriver webDriver;

    private DriverFactory() {}

    public static DriverFactory getInstance() {
        return instance;
    }

    public  WebDriver getDriver() {
        return driver.get();
    }

    public void setUp() {
        initDriver();
        driver.set(webDriver);
    }

    public void initDriver() {
        String runType = utils.getProperties("runType");

        switch (runType.toUpperCase()) {
            case "CHROME":
                runChrome();
                break;
            case "FIREFOX":
                runFireFox();
                break;
            default:
                throw new IllegalArgumentException("Run type '" + runType + "' isn't supported." );
        }
        webDriver.manage().window().maximize();
        webDriver.manage().timeouts().implicitlyWait(TIME_OUT, TimeUnit.SECONDS);
    }


    protected void runChrome() {
        WebDriverManager.chromedriver().setup();
        ChromeOptions chromeOptions = new ChromeOptions();
        chromeOptions.setAcceptInsecureCerts(true);

        String OS = System.getProperty("os.name").toLowerCase();
        if( OS.contains("linux") ) {
           System.out.println("==== Linux ======");
           chromeOptions.addArguments("--headless");
        }
        chromeOptions.addArguments("--allowed-ips","--ignore-certificate-errors","--disable-web-security", "--allow-running-insecure-content");
        chromeOptions.addArguments("--incognito");
        chromeOptions.addArguments("--disable-blink-features");
        chromeOptions.addArguments("--disable-blink-features=AutomationControlled");

        webDriver = new ChromeDriver(chromeOptions);

        webDriver.get(utils.getProperties("webUrl"));
    }

    private void runFireFox() {
        WebDriverManager.firefoxdriver().setup();
        webDriver = new FirefoxDriver();
        webDriver.get(utils.getProperties("webUrl"));
    }

    public void closeDriver() {
        driver.get().quit();
        driver.remove();
    }
}
