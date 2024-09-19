package BaseFactory;

import WebDriverFactory.DriverFactory;
import com.aventstack.extentreports.ExtentReports;
import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import StepDefinitions.HookClass;


public abstract class BaseTestClass  {

    public WebDriver driver;
    protected static final Logger logger = LoggerFactory.getLogger(BaseTestClass.class);
    public ExtentReports extent;
   // protected Logger logger;
    public DriverFactory driverFactory =new DriverFactory();

    @BeforeMethod(alwaysRun = true)
    @Parameters({"browser"})

    public WebDriver createDriver() {
        driver = DriverFactory.createAndReturnDriver();
        driver.manage().window().maximize();
        logger.info("Browser launched and maximized");
        return driver;
    }


    @AfterMethod(alwaysRun = true)
    public void tearDown(ITestResult result) {
        if (result.getStatus() == ITestResult.FAILURE) {
            HookClass.getExtentObject().getTest().fail(result.getThrowable());
            logger.error("Test failed: " + result.getName(), result.getThrowable());
        } else if (result.getStatus() == ITestResult.SUCCESS) {
            HookClass.getExtentObject().getTest().pass("Test passed");
            logger.info("Test passed: " + result.getName());
        } else {
            HookClass.getExtentObject().getTest().skip("Test skipped");
            logger.warn("Test skipped: " + result.getName());
        }
        DriverFactory.quitDriver();
        HookClass.getExtentObject().endTest();
        logger.info("Browser closed and resources cleaned up");
    }
}