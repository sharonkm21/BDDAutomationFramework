package Report;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;
import io.cucumber.java.Scenario;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;

import java.io.File;
import java.io.IOException;
import java.lang.management.ManagementFactory;
import java.net.InetAddress;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ExtentReportManager {
    public ExtentReports extent;
    public ExtentTest test;
    public ExtentSparkReporter sparkReporter;
    public ExtentTest suiteTest;

    public ExtentReportManager(Scenario scenario)
    {
        createReportInstance("target/reports/ExtentReport_" + getCurrentTime() + ".html");
        createTest(scenario.getName());
    }
    // Get the instance of ExtentReports (Singleton pattern)
    public ExtentReports getInstance() {
        if (extent == null) {
            extent=createReportInstance("target/reports/ExtentReport_" + getCurrentTime() + ".html");
        }
        return extent;
    }

    public ExtentReports createReportInstance(String filePath) {
        sparkReporter = new ExtentSparkReporter(filePath);

        sparkReporter.config().setEncoding("utf-8");
        sparkReporter.config().setDocumentTitle("Automation Test Report");
        sparkReporter.config().setReportName("Automation Test Results");
        sparkReporter.config().setTheme(Theme.STANDARD);

        extent = new ExtentReports();
        extent.attachReporter(sparkReporter);
        addSystemInfo();
        return extent;
    }

    // Add System Information
    private void addSystemInfo() {
        try {
            extent.setSystemInfo("Operating System", System.getProperty("os.name"));
            extent.setSystemInfo("Java Version", System.getProperty("java.version"));
            extent.setSystemInfo("User", System.getProperty("user.name"));
            extent.setSystemInfo("Machine Name", InetAddress.getLocalHost().getHostName());
            extent.setSystemInfo("JVM Uptime", String.valueOf(ManagementFactory.getRuntimeMXBean().getUptime()) + " ms");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Create a suite in the report
    public ExtentTest startSuite(String suiteName, String suiteDescription) {
        suiteTest = extent.createTest(suiteName, suiteDescription);
        return suiteTest;
    }

    // Create a test under the suite
    public ExtentTest createTest(String testName, String description) {
        test = suiteTest.createNode(testName, description);  // Associate test under the suite
        return test;
    }

    // Add logs to the suite level
    public void logToSuite(String message) {
        if (suiteTest != null) {
            suiteTest.info(message);
        }
    }

    // Add a warning to the suite level
    public void logSuiteWarning(String message) {
        if (suiteTest != null) {
            suiteTest.warning(message);
        }
    }

    public ExtentTest getTest()
    {
        return test;
    }

    // Flush the report to ensure all logs are written
    public void flushReport() {
        if (extent != null) {
            extent.flush();
        }
    }

    // Utility function to get the current time in a readable format
    private String getCurrentTime() {
        return new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss").format(new Date());
    }
    // Create a new test
    public ExtentTest createTest(String testName) {
        test = getInstance().createTest(testName);
        return test;
    }

    // End the report (flush at the end)
    public void endTest() {
        getInstance().flush();
    }

    // Log failure details and capture screenshot
    public void logFailure(String stepName, WebDriver driver) {
        captureScreenshot(stepName,driver);
        test.fail(stepName + " failed: ");
    }

    // Log test pass with or without a screenshot
    public void logPass(String message, boolean takeScreenshot,WebDriver driver) {
        if (takeScreenshot) {
            String screenshotPath = captureScreenshot("Pass_" + System.currentTimeMillis(),driver);
            test.pass(message, MediaEntityBuilder.createScreenCaptureFromPath(screenshotPath).build());
        } else {
            test.pass(message);
        }
        Assert.assertTrue(true, message); // Simulate a pass assertion
    }

    // Log test info messages
    public void logInfo(String message) {
        test.info(message);
    }

    // Log test fail with an option to take a screenshot and fail hard or soft
    public void logFail(String message, boolean isHardFail, boolean takeScreenshot,WebDriver driver) {
        if (takeScreenshot) {
            String screenshotPath = captureScreenshot("Fail_" + System.currentTimeMillis(),driver);
            test.fail(message, MediaEntityBuilder.createScreenCaptureFromPath(screenshotPath).build());
        } else {
            test.fail(message);
        }

        if (isHardFail) {
            Assert.fail(message); // Hard fail to stop execution
        }
    }

    // Soft fail (no hard assertion, allows test to continue)
    public void logSoftFail(String message, boolean takeScreenshot,WebDriver driver) {
        if (takeScreenshot) {
            String screenshotPath = captureScreenshot("SoftFail_" + System.currentTimeMillis(),driver);
            test.fail(message, MediaEntityBuilder.createScreenCaptureFromPath(screenshotPath).build());
        } else {
            test.fail(message);
        }
        // Do not assert fail here to allow continuation of test execution
    }

    // Log test execution time
    public void logExecutionTime(long startTime, long endTime) {
        long executionTime = endTime - startTime;
        test.info("Test executed in: " + executionTime + " ms");
    }

    // Capture screenshot
    public String captureScreenshot(String fileName,WebDriver driver) {
        File scrFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        String filePath = "screenshots/" + fileName + ".png";
        try {
            FileUtils.copyFile(scrFile, new File(filePath));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return filePath;
    }

    // Create directory if it does not exist
    private static void createDirectoryIfNotExists(String directoryPath) {
        File directory = new File(directoryPath);
        if (!directory.exists()) {
            directory.mkdirs();
        }
    }
}
