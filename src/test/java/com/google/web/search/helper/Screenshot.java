package com.google.web.search.helper;

import com.epam.reportportal.listeners.ItemStatus;
import com.epam.reportportal.message.ReportPortalMessage;
import com.epam.reportportal.service.Launch;
import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.slf4j.helpers.MessageFormatter;
import org.testng.ITestNGMethod;
import org.testng.ITestResult;
import org.testng.Reporter;

import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Screenshot {
    private static final Logger logger = LogManager.getLogger(Screenshot.class);

    public void reportPortalScreenshot(ITestResult result, WebDriver driver) {
 //       ReportPortalMessage message = null;
        TakesScreenshot ts = (TakesScreenshot)driver;
        File srcFile = ts.getScreenshotAs(OutputType.FILE);

        try {
            FileUtils.copyFile(srcFile, new File("./screenshots/" + result.getName() + ".png"));
            logger.info("Screenshot taken");
            ReportPortalMessage message = new ReportPortalMessage(srcFile, "Screenshot taken");
        } catch (IOException e) {
            logger.error(MessageFormatter.format("Exception while taking screenshot {0}", e.getMessage()));
        }

    /*    boolean emitLogResult = ReportPortal.emitLog("attached screenshot - ReportPortal.emitLog", "INFO", Calendar.getInstance().getTime(), srcFile);
        log.info("screenshot via ReportPortal.emitLog: {}", emitLogResult);

        boolean result = ReportPortal.emitLaunchLog("attached screenshot - ReportPortal.emitLaunchLog", "INFO", Calendar.getInstance().getTime(), srcFile);
        log.info("screenshot via ReportPortal.emitLaunchLog: {}", result);
*/
        Launch.currentLaunch().getStepReporter().sendStep(ItemStatus.INFO, "attached screenshot - sendStep", srcFile);
    }

    public void screenShot(WebDriver driver, ITestResult result) {
        TakesScreenshot ts = (TakesScreenshot)driver;
        File srcFile = ts.getScreenshotAs(OutputType.FILE);
        File screenShotName = new File("./screenshots/" + result.getName() + ".png");
        try {
            FileUtils.copyFile(srcFile, screenShotName);
            String absolutePath = screenShotName.getAbsolutePath();
            int startIndex = absolutePath.indexOf(".");
            String relativePath = absolutePath.substring(startIndex ).replace(".\\","");
            String name = relativePath .replace('\\','/');

            Reporter.log("<a href=" + name + " target='_blank' >" + absolutePath + "</a>");           // Reporter.log("<br><img src='”+screenshotName+”‘ height=’300’ width=’300’/><br>");
        }catch (Exception e) {
            logger.error(MessageFormatter.format("Exception while taking screenshot {0}", e.getMessage()));
        }
    }

    /*private String getFileName(String nameTest) throws IOException {
        DateFormat dateFormat = new SimpleDateFormat("MM-dd-yyyy_hh.mm.ss");
        Date date = new Date();
        return dateFormat.format(date) + "_" + nameTest + ".png";
    }

    private String getPath(String nameTest) throws IOException {
        File directory = new File(".");
        return directory.getCanonicalPath() + "/target/surefire-reports/screenShots/" + getFileName(nameTest);
    }*/
}
