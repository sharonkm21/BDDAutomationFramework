package Utility;

import StepDefinitions.HookClass;
import WebDriverFactory.DriverFactory;
import com.aventstack.extentreports.ExtentTest;
import org.openqa.selenium.WebDriver;
import org.testng.*;

import Report.ExtentReportManager;

public class ExtentReportListener implements ITestListener, ISuiteListener {

    private static ExtentReportManager extentReporter = HookClass.getExtentObject();
    private static ThreadLocal<ExtentTest> test = new ThreadLocal<>();
    private static ThreadLocal<ExtentTest> suiteTest = new ThreadLocal<>();
    private WebDriver driver;

    // Called when the test suite starts
    @Override
    public void onStart(ISuite suite) {
        // Log suite start in the Extent report
        ExtentTest suiteExtentTest = extentReporter.startSuite(suite.getName(), "Suite: " + suite.getName() + " started.");
        suiteTest.set(suiteExtentTest);  // Associate suite-level ExtentTest to the current thread
        extentReporter.logToSuite("Starting Test Suite: " + suite.getName());
    }

    // Called when the test suite finishes
    @Override
    public void onFinish(ISuite suite) {
        // Log suite completion
        extentReporter.logToSuite("Completed Test Suite: " + suite.getName());
        extentReporter.flushReport();  // Ensure the report is flushed after the suite
    }

    // Called when the test starts
    @Override
    public void onTestStart(ITestResult result) {
        String testName = result.getMethod().getMethodName();
        String description = result.getMethod().getDescription() != null ? result.getMethod().getDescription() : testName;
        // Create a new test in the report
        ExtentTest extentTest = extentReporter.createTest(testName, description);
        test.set(extentTest);  // Set the ExtentTest instance for the current thread
    }

    // Called when a test passes
    @Override
    public void onTestSuccess(ITestResult result) {
        test.get().pass("Test Passed: " + result.getMethod().getMethodName());
        extentReporter.flushReport();  // Flush the report to ensure logs are written
    }

    // Called when a test fails
    @Override
    public void onTestFailure(ITestResult result) {
        String browserType = result.getTestContext().getCurrentXmlTest().getParameter("browser");

        driver = DriverFactory.getWebDriver();  // Retrieve WebDriver instance for taking a screenshot

        String methodName = result.getMethod().getMethodName();
        String errorMessage = result.getThrowable() != null ? result.getThrowable().getMessage() : "No error message available";

        // Log failure with a screenshot
        extentReporter.logFail(methodName + " failed", true, true, driver);
        extentReporter.logInfo("Error Message: " + errorMessage);

        extentReporter.flushReport();  // Flush the report after the failure is logged
    }

    // Called when a test is skipped
    @Override
    public void onTestSkipped(ITestResult result) {
        test.get().skip("Test Skipped: " + result.getMethod().getMethodName());
        extentReporter.flushReport();  // Ensure the report is flushed after a skipped test
    }

    // Called when a test fails but within success percentage threshold
    @Override
    public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
        test.get().fail("Test failed but within success percentage: " + result.getMethod().getMethodName());
        extentReporter.flushReport();  // Flush the report
    }

    // Called when the test context starts (could be used for additional suite-level logging)
    @Override
    public void onStart(ITestContext context) {
        // Optionally, log context start if needed
        extentReporter.logToSuite("Starting Test Context: " + context.getName());
    }

    // Called when the test context finishes (could be used for additional suite-level logging)
    @Override
    public void onFinish(ITestContext context) {
        extentReporter.logToSuite("Finished Test Context: " + context.getName());
        extentReporter.flushReport();  // Ensure the report is flushed after the test context
    }
}
