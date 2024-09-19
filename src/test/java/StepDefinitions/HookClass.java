package StepDefinitions;

import BaseFactory.BaseTestClass;
import Report.ExtentReportManager;
import WebDriverFactory.DriverFactory;
import com.aventstack.extentreports.Status;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;

public class HookClass extends BaseTestClass {

    public static ExtentReportManager extentReporter;

    @Before
    public void setup(Scenario scenario) {
        String browserType = System.getProperty("browser", "chrome");  // Get browser type from system property or default to chrome
        System.out.println("Starting scenario: " + scenario.getName() + " on browser: " + browserType);
        driver=createDriver();  // Initialize WebDriver for the specified browser
        extentReporter=new ExtentReportManager(scenario);
        extentReporter.test.log(Status.INFO, "Starting scenario: " + scenario.getName());
    }
    public static ExtentReportManager getExtentObject()
    {
        return  extentReporter;
    }
    @After
    public void tearDown(Scenario scenario) {
        if (scenario.isFailed()) {
            extentReporter.test.log(Status.FAIL, "Scenario failed: " + scenario.getName());

            // Capture screenshot on failure
            byte[] screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
            String screenshotPath = extentReporter.captureScreenshot(scenario.getName(), driver);
            extentReporter.test.addScreenCaptureFromPath(screenshotPath);  // Attach screenshot to the report

        } else {
            extentReporter.test.log(Status.PASS, "Scenario passed: " + scenario.getName());
        }

        // Close the driver (from BaseTest)
        DriverFactory.quitDriver();
        // Ensure the report is flushed
        extentReporter.flushReport();
    }
}