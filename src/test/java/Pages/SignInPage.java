package Pages;

import Report.ExtentReportManager;
import StepDefinitions.HookClass;
import Utility.CommonUtilities;
import WebDriverFactory.DriverFactory;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class SignInPage {
    WebDriver driver;
    CommonUtilities utils;

    @FindBy(id = "email")
    WebElement txtbx_emailField;

    @FindBy(xpath = "//input[@type='password']")
    WebElement txtxb_passwordField;

    @FindBy(xpath = "//button[@class='action login primary' and @id='send2']")
    WebElement signInButton;

    @FindBy(xpath = "//div[contains(@class,'error message')]//div[contains(text(),'The account sign-in was incorrect or your account is disabled temporarily. Please wait and try again later.')]")
    WebElement txt_invalidPasswordMsg;

    @FindBy(xpath = "(//span[@class='customer-name']//button)[1]")
    WebElement btn_arwDown;

    @FindBy(xpath = "//li[@class='authorization-link']//a[contains(text(),'Sign Out')]")
    WebElement btn_signOut;

    @FindBy(xpath = "//*[contains(text(),'You are signed out')]")
    WebElement txt_customerLogoutPage;

    @FindBy(xpath = "//div[contains(@id,'email_address-error') and contains(text(),'Please enter a valid email address (Ex: johndoe@domain.com).')]")
    WebElement txt_invalidEmailFormatMessage;

    @FindBy(xpath = "//span[text()='My Account']")
    WebElement txt_myAccountHeader;



    public SignInPage(WebDriver driver) {
        this.driver = DriverFactory.getWebDriver();
        utils = new CommonUtilities(this.driver);
        PageFactory.initElements(this.driver, this);
        ExtentReportManager extentReporter = HookClass.getExtentObject();
    }

    public void enterLoginDetails(String email, String password) {
        utils.sendKeys(txtbx_emailField, email, "Email Textbox");
        utils.sendKeys(txtxb_passwordField, password, "Password Textbox");
    }

    public void clickSignIn() {
        utils.clickUsingJS(signInButton,"Sign In Button");
    }

    public void verifySuccessLogin() {
        String fname = utils.readProperty("UserFirstName");
        String lname = utils.readProperty("UserLastName");
        utils.waitForElementVisible(txt_myAccountHeader);
        utils.verifyIfElementPresent("//p[contains(text(),'" + fname + " " + lname + "')]", "xpath", "Name");
    }

    public void verifyInvalidLogin() {
        utils.verifyIfElementPresent(txt_invalidPasswordMsg, "Invalid Login Message");
    }

    public void signOutFromAccount() {
        utils.clickUsingJS(btn_arwDown, "Arrow Down Button");
        utils.clickUsingJS(btn_signOut, "Sign Out button");
    }

    public void verifyLogoutSuccessful()
    {
        utils.verifyIfElementPresent(txt_customerLogoutPage,"Logout");
    }

    public void verifyInvalidEmailFormat()
    {
        utils.verifyIfElementPresent(txt_invalidEmailFormatMessage,"Invalid Email Format");
    }
}
