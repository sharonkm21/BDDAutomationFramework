package Utility;

import Report.ExtentReportManager;
import StepDefinitions.HookClass;
import WebDriverFactory.DriverFactory;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.IOException;
import java.time.Duration;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.Set;

public class CommonUtilities
{
    public WebDriver driver;
    public WebDriverWait waitElement;
    public WebDriverWait waitPage;
    public WebDriverWait waitWindow;
    public Actions actions;
    public JavascriptExecutor jsExecutor;
    public ExtentReportManager reporter;

    public CommonUtilities(WebDriver driver) {
        this.driver = DriverFactory.getWebDriver();
        waitElement = new WebDriverWait(this.driver, Duration.ofSeconds(10));
        waitPage = new WebDriverWait(this.driver, Duration.ofSeconds(30));
        waitWindow = new WebDriverWait(this.driver, Duration.ofSeconds(20));
        actions = new Actions(this.driver);
        jsExecutor = (JavascriptExecutor) this.driver;
        reporter= HookClass.getExtentObject();
    }

    public WebElement getShadowRoot(By shadowHostLocator) {
        WebElement shadowHost = driver.findElement(shadowHostLocator);
        return (WebElement) jsExecutor.executeScript("return arguments[0].shadowRoot", shadowHost);
    }

    public boolean waitForElementToBeClickable(WebElement locator) {
        try {
            waitElement.until(ExpectedConditions.elementToBeClickable(locator));
            reporter.logPass("Element clickable."+ locator.toString(),false,driver);
            return false;
        }
        catch (Exception e1)
        {
            reporter.logSoftFail("Element not clickable: ERROR:"+e1.getMessage(),true,driver);
            return false;
        }
    }

    public void selectShadowDropdown(By shadowHostLocator, By dropdownLocator, String optionText) {
        try {
            // Get the shadow root element
            WebElement shadowRoot = getShadowRoot(shadowHostLocator);

            // Find the dropdown inside the shadow DOM
            WebElement dropdown = shadowRoot.findElement(dropdownLocator);
            dropdown.click();

            // Find the option by its visible text
            String optionXPath = String.format("//option[contains(text(), '%s')]", optionText);
            WebElement option = shadowRoot.findElement(By.xpath(optionXPath));
            option.click();
        } catch (Exception e) {
            System.err.println("Failed to select shadow DOM dropdown option: " + e.getMessage());
        }
    }

    public void waitForElementVisible(WebElement locator) {
        try{
            waitElement.until(ExpectedConditions.visibilityOf(locator));
            reporter.logInfo("Element present on screen.");
        }
        catch (Exception e)
        {
            reporter.logFail("Element not present on screen",true,true,driver);
        }

    }

    public void clickUsingJS(WebElement locator,String userMessage) {
        try {
            waitForElementToBeClickable(locator);  // Wait for element to be clickable
            jsExecutor.executeScript("arguments[0].click();", locator); // Execute JavaScript click
            reporter.logPass("User is able to click on "+userMessage,false,driver);
        }
        catch (Exception e)
        {
            reporter.logFail("Unable to click on "+userMessage+" \nERROR: "+e.getMessage(),true,true,driver);
        }
    }

    public void clickUsingJSWithScroll(WebElement locator) {
        try {
            waitForElementToBeClickable(locator);  // Wait for element to be clickable
            jsExecutor.executeScript("arguments[0].scrollIntoView(true);", locator);  // Scroll into view
            jsExecutor.executeScript("arguments[0].click();", locator);  // Execute JavaScript click
        } catch (Exception e) {
            throw new RuntimeException("Failed to click element using JS with scroll: " + e.getMessage());
        }
    }

    public void clickUsingActionsWithScroll(WebElement locator) {
        try {
            waitForElementToBeClickable(locator);  // Wait for element to be clickable
            jsExecutor.executeScript("arguments[0].scrollIntoView(true);", locator);  // Scroll into view
            Actions actions = new Actions(driver);
            actions.moveToElement(locator).click().perform();  // Perform Actions click
        } catch (Exception e) {
            throw new RuntimeException("Failed to click element using Actions with scroll: " + e.getMessage());
        }
    }

    public void hoverAndClick(By hoverLocator, By clickLocator) {
        try {
            WebElement hoverElement = waitElement.until(ExpectedConditions.visibilityOfElementLocated(hoverLocator));
            WebElement clickElement = waitElement.until(ExpectedConditions.elementToBeClickable(clickLocator));

            actions.moveToElement(hoverElement).pause(Duration.ofSeconds(1)) // Hover with a small pause
                    .moveToElement(clickElement)
                    .click()
                    .build()
                    .perform();
        } catch (Exception e) {
            throw new RuntimeException("Failed to hover and click: " + e.getMessage());
        }
    }

    public void clickUsingActions(WebElement locator) {
        try {
            waitForElementToBeClickable(locator);  // Wait for element to be clickable
            Actions actions = new Actions(driver);
            actions.moveToElement(locator).click().perform();  // Perform Actions click
        } catch (Exception e) {
            throw new RuntimeException("Failed to click element using Actions: " + e.getMessage());
        }
    }

    public void rightClick(By locator) {
        try {
            WebElement element = waitElement.until(ExpectedConditions.elementToBeClickable(locator));
            actions.contextClick(element).perform(); // Perform right-click action
        } catch (Exception e) {
            throw new RuntimeException("Failed to right-click on element: " + e.getMessage());
        }
    }

    public void clickElement(WebElement locator,String userMessage)
    {
        try{
            waitForElementToBeClickable(locator);
            jsExecutor.executeScript("arguments[0].scrollIntoView(true);", locator);  // Scroll into view
            waitForElementVisible(locator);
            locator.click();
            reporter.logPass("User is able to click on "+userMessage,false,driver);
        }
        catch (Exception e)
        {
            reporter.logFail("Unable to click on "+userMessage+" \nERROR: "+e.getMessage(),true,true,driver);
        }

    }

    public void selectByVisibleText(By locator, String visibleText) {
        try {
            WebElement dropdownElement = driver.findElement(locator);
            Select dropdown = new Select(dropdownElement);
            dropdown.selectByVisibleText(visibleText);
        } catch (Exception e) {
            System.err.println("Failed to select dropdown by visible text: " + e.getMessage());
        }
    }

    public void selectByValue(By locator, String value) {
        try {
            WebElement dropdownElement = driver.findElement(locator);
            Select dropdown = new Select(dropdownElement);
            dropdown.selectByValue(value);
        } catch (Exception e) {
            System.err.println("Failed to select dropdown by value: " + e.getMessage());
        }
    }

    public void selectByIndex(By locator, int index) {
        try {
            WebElement dropdownElement = driver.findElement(locator);
            Select dropdown = new Select(dropdownElement);
            dropdown.selectByIndex(index);
        } catch (Exception e) {
            System.err.println("Failed to select dropdown by index: " + e.getMessage());
        }
    }

    public By getLocator(String locatorValue, String locatorType) {
        switch (locatorType.toLowerCase()) {
            case "id":
                return By.id(locatorValue);
            case "name":
                return By.name(locatorValue);
            case "xpath":
                return By.xpath(locatorValue);
            case "css":
            case "cssselector":
                return By.cssSelector(locatorValue);
            case "classname":
                return By.className(locatorValue);
            case "linktext":
                return By.linkText(locatorValue);
            case "partiallinktext":
                return By.partialLinkText(locatorValue);
            case "tagname":
                return By.tagName(locatorValue);
            default:
                throw new IllegalArgumentException("Unknown locator type: " + locatorType);
        }
    }

    public void clickElement(String locatorValue, String locatorType) {
        By locator = getLocator(locatorValue, locatorType);
        WebElement element= waitElement.until(ExpectedConditions.visibilityOfElementLocated(locator));
        element.click();
    }

    public void sendKeys(String locatorValue, String locatorType, String text,String userMessage)
    {
        try
        {
            By locator = getLocator(locatorValue, locatorType);
            waitElement.until(ExpectedConditions.visibilityOfElementLocated(locator));
            WebElement element =waitElement.until(ExpectedConditions.elementToBeClickable(locator));
            element.clear();
            element.sendKeys(text);
            reporter.logInfo("Text Entered:"+text+" in textbox "+userMessage);
        }
        catch (Exception e)
        {
            reporter.logFail("Not able to enter text in textbox "+userMessage+".\n Error:"+e.getMessage(),true,true,driver);
        }
    }

    public void sendKeys(WebElement locator, String text , String userMessage) {
        try
        {
            waitForElementVisible(locator);
            waitForElementToBeClickable(locator);
            locator.clear();
            locator.sendKeys(text);
            reporter.logInfo("Text Entered:"+text+" in textbox "+userMessage);
        }
        catch (Exception e)
        {
            reporter.logFail("Not able to enter text in textbox "+userMessage+".\n Error:"+e.getMessage(),true,true,driver);
        }
    }

    public void selectCustomDropdown(By dropdownLocator, By optionLocator) {
        try {
            // Open the dropdown
            WebElement dropdownElement = driver.findElement(dropdownLocator);
            dropdownElement.click();

            // Wait for and click the option
            WebElement optionElement = driver.findElement(optionLocator);
            optionElement.click();
        } catch (Exception e) {
            System.err.println("Failed to select option from custom dropdown: " + e.getMessage());
        }
    }

    public void selectCustomDropdownByVisibleText(By dropdownLocator, String optionVisibleText) {
        try {
            // Click to open the dropdown
            WebElement dropdown = driver.findElement(dropdownLocator);
            dropdown.click();

            // Build an XPath to locate the dropdown option by visible text
            String optionXPath = String.format("//li[contains(text(), '%s')]", optionVisibleText);
            WebElement option = driver.findElement(By.xpath(optionXPath));

            // Using Actions to click the option
            Actions actions = new Actions(driver);
            actions.moveToElement(option).click().perform();
        } catch (Exception e) {
            System.err.println("Failed to select custom dropdown by visible text: " + e.getMessage());
        }
    }

    public void switchToWindow(String locatorValue, String locatorType) throws NoSuchElementException, TimeoutException {
        String currentWindow = driver.getWindowHandle();
        Set<String> allWindows = driver.getWindowHandles();
        boolean windowFound = false;

        try {
            for (String window : allWindows) {
                driver.switchTo().window(window);

                // Wait for the page to load before checking the condition
                waitWindow.until(webDriver -> !((WebDriver) webDriver).getTitle().isEmpty());

                // Find window based on locatorType
                switch (locatorType.toLowerCase()) {
                    case "title":
                        if (driver.getTitle().equals(locatorValue)) {
                            windowFound = true;
                            return;
                        }
                        break;
                    case "partialtitle":
                        if (driver.getTitle().contains(locatorValue)) {
                            windowFound = true;
                            return;
                        }
                        break;
                    case "url":
                        if (driver.getCurrentUrl().equals(locatorValue)) {
                            windowFound = true;
                            return;
                        }
                        break;
                    case "partialurl":
                        if (driver.getCurrentUrl().contains(locatorValue)) {
                            windowFound = true;
                            return;
                        }
                        break;
                    default:
                        throw new IllegalArgumentException("Unknown locator type: " + locatorType);
                }
            }

            // If no matching window is found, switch back to the original window
            if (!windowFound) {
                driver.switchTo().window(currentWindow);
                throw new NoSuchElementException("No window found with " + locatorType + ": " + locatorValue);
            }

        } catch (TimeoutException te) {
            driver.switchTo().window(currentWindow);
            throw new TimeoutException("Window did not load in time when searching for " + locatorType + ": " + locatorValue, te);

        } catch (WebDriverException we) {
            driver.switchTo().window(currentWindow);
            throw new WebDriverException("Error occurred while switching windows: " + we.getMessage(), we);
        }
    }

    public void handlePopup(WebDriver driver, By popupLocator, By buttonLocator) {
        try {
            // Wait for the pop-up to appear and handle it
            WebElement popup = waitElement.until(ExpectedConditions.visibilityOfElementLocated(popupLocator));
            WebElement closeButton = popup.findElement(buttonLocator);
            closeButton.click();
            System.out.println("Pop-up closed successfully.");
        } catch (Exception e) {
            System.out.println("No pop-up present or could not close the pop-up: " + e.getMessage());
        }
    }

    public void switchToFrameByNameOrId(WebDriver driver, String nameOrId) {
        waitElement.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(nameOrId));
        System.out.println("Switched to frame by name or ID: " + nameOrId);
    }

    public void switchToFrameByElement(WebDriver driver, WebElement frameElement) {
        waitElement.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(frameElement));
        System.out.println("Switched to frame by WebElement.");
    }

    public void switchToDefaultContent(WebDriver driver) {
        driver.switchTo().defaultContent();
        System.out.println("Switched back to the default content.");
    }

    public void switchToFrameByIndex(WebDriver driver, int index) {
            waitElement.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(index));
            System.out.println("Switched to frame by index: " + index);
    }

    public void closeCurrentTab(WebDriver driver) {
    // Check if there are multiple tabs open
        if (driver.getWindowHandles().size() > 1) {
            driver.close();
        } else {
            driver.quit();
        }
    }

    public boolean isElementPresent(WebDriver driver, By locator, int timeoutInSeconds) {
        try {
            WebElement element = waitElement.until(ExpectedConditions.visibilityOfElementLocated(locator));
            return element != null;
        } catch (NoSuchElementException | TimeoutException e) {
            return false;
        }
    }

    public boolean isElementSelected(WebDriver driver, By locator, int timeoutInSeconds) {
        try {
            WebElement element = waitElement.until(ExpectedConditions.visibilityOfElementLocated(locator));
            return element.isSelected();
        } catch (NoSuchElementException | TimeoutException e) {
            return false;
        }
    }

    public String getElementText(WebDriver driver, By locator, int timeoutInSeconds) {
        try {
            WebElement element = waitElement.until(ExpectedConditions.visibilityOfElementLocated(locator));
            return element.getText();
        } catch (NoSuchElementException | TimeoutException e) {
            return null;
        }
    }

    public String getElementAttribute(WebDriver driver, By locator, String attribute, int timeoutInSeconds) {
        try {
            WebElement element = waitElement.until(ExpectedConditions.visibilityOfElementLocated(locator));
            return element.getAttribute(attribute);
        } catch (NoSuchElementException | TimeoutException e) {
            return null;
        }
    }

    public String getInputValue(WebDriver driver, By locator, int timeoutInSeconds) {
        return getElementAttribute(driver, locator, "value", timeoutInSeconds);
    }
    public void closeBrowser() {
        driver.quit();
    }

    public void navigateTo(String url) {
        try {
            reporter.logInfo("Navigating to URL: " + url);
            driver.get(url);
        } catch (Exception e) {
            reporter.logFail("Could not navigate to " + url, true, true,driver);
        }
    }

    public boolean verifyIfElementPresent(WebElement locator,String userMessage) {
        try {
            waitElement.until(ExpectedConditions.visibilityOf(locator));
            reporter.logPass("Element for "+ userMessage+ "found.",true,driver);
            return true;
        } catch (NoSuchElementException e)
        {   reporter.logFail("Element for "+ userMessage+" not found. \n+ERROR: "+e.getMessage(),true,true,driver);

            return false;
        }
    }

    public boolean verifyIfElementPresent(String locatorValue, String locatorType, String userMessage) {
        try {
            By locator = getLocator(locatorValue, locatorType);
            WebElement element= waitElement.until(ExpectedConditions.visibilityOfElementLocated(locator));
            reporter.logPass("Element for "+ userMessage+ " is present and visible.",true,driver);
            return true;
        } catch (NoSuchElementException e)
        {   reporter.logFail("Element for "+ userMessage+" not found. \n+ERROR: "+e.getMessage(),true,true,driver);

            return false;
        }
    }

    public void writeProperty(String key, String value)
    {
        try{
            ExcelUtility excelWrite= new ExcelUtility();
            FileOutputStream output = new FileOutputStream(GlobalVariables.CREATED_PROP_FILE_PATH);
            GlobalVariables.prop.setProperty(key, value);
            GlobalVariables.prop.store(output, "Updated properties file");
            reporter.logPass("Successfully written "+key+" : "+value+" pair to properties file.",false,driver);
            excelWrite.updateExcelFromProperties(GlobalVariables.CREATED_PROP_FILE_PATH,key,value);
        } catch (IOException e) {
            reporter.logFail("ERROR:"+e.getMessage(),true,false,driver);

        }
    }

    public String readProperty(String key) {

        try (FileInputStream input = new FileInputStream(GlobalVariables.CREATED_PROP_FILE_PATH)) {
            GlobalVariables.prop.load(input);
            String value =   GlobalVariables.prop.getProperty(key);
            if (value != null) {
               return value;
            } else {
                reporter.logFail("Key: "+ key +" not found",true,false,driver);
                return null;
            }
        } catch (IOException e) {
            reporter.logFail("ERROR:"+e.getMessage(),true,false,driver);
            return null;
        }
    }

}
