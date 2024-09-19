package WebDriverFactory;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.TimeUnit;

public class DriverFactory
{
    private static ThreadLocal<WebDriver> webDriver = new ThreadLocal<WebDriver>();
    private static final Logger logger =  LoggerFactory.getLogger(DriverFactory.class);

    public static void createDriver() {
        String browserName = System.getProperty("browser");

        // headless value passed from command line
        String headless =System.getProperty("headless");

        // if browser name value is not passed from commandline then by default test would run on chrome
        if (browserName == null)
            browserName = "chrome";

        // Determine if we should run headless (from system properties or CI environment variables)
            boolean isHeadless = shouldRunHeadless();
            switch (browserName.toLowerCase()) {
                case "chrome":
                    WebDriverManager.chromedriver().setup();
                    ChromeOptions chromeOptions = new ChromeOptions();
                    if (isHeadless) {
                       // chromeOptions.addArguments("--headless");
                       // chromeOptions.addArguments("--disable-gpu");
                        chromeOptions.addArguments("--start-maximized");
                        chromeOptions.addArguments("--remote-allow-origins=*"); // Necessary for remote WebDriver use
                    }
                    webDriver.set(new ChromeDriver(chromeOptions));
                    break;

                case "firefox":
                    WebDriverManager.firefoxdriver().setup();
                    FirefoxOptions firefoxOptions = new FirefoxOptions();
                    if (isHeadless) {
                        firefoxOptions.addArguments("--headless");
                    }
                    webDriver.set(new FirefoxDriver(firefoxOptions));
                    break;

                case "edge":
                    WebDriverManager.edgedriver().setup();
                    EdgeOptions edgeOptions = new EdgeOptions();
                    if (isHeadless) {
                        edgeOptions.addArguments("--headless");
                    }
                    webDriver.set(new EdgeDriver(edgeOptions));
                    break;

                default:
                    throw new IllegalArgumentException("Unsupported browser: " + browserName);
            }

            // Maximize the browser window after initializing the WebDriver
            if (!isHeadless) {  // Maximizing only in non-headless mode
                getWebDriver().manage().window().maximize();
            }
            getWebDriver().manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
            getWebDriver().manage().timeouts().pageLoadTimeout(30, TimeUnit.SECONDS);
             logger.info("WebDriver initialized successfully for browser: {}", browserName);

        }

    public static WebDriver createAndReturnDriver() {
        createDriver();
        return webDriver.get();
    }

    public static WebDriver getWebDriver() {
        System.out.println("WebDriver: " + webDriver.get());
        return webDriver.get();
    }
    public static void quitDriver() {
        if ( getWebDriver() != null) {
            logger.info("Quitting WebDriver instance");
            getWebDriver().quit();
        }
    }

        // Determine if tests should run in headless mode
        private static boolean shouldRunHeadless() {
            String headless = System.getProperty("headless", "false");
            String ciEnvironment = System.getenv("CI");

            // If running in a CI environment (like Jenkins), default to headless
            return headless.equalsIgnoreCase("true") || (ciEnvironment != null && ciEnvironment.equalsIgnoreCase("true"));
        }

}
