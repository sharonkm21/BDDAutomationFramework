package Pages;

import Report.ExtentReportManager;
import StepDefinitions.HookClass;
import Utility.CommonUtilities;
import WebDriverFactory.DriverFactory;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class RegistrationPage {
    WebDriver driver;
    CommonUtilities utils;

    @FindBy(id = "firstname")
    WebElement txtbx_firstname;

    @FindBy(id = "lastname")
    WebElement txtbx_lastname;

    @FindBy(id = "email_address")
    WebElement txtbx_email;

    @FindBy(id = "password")
    WebElement txtbx_password;

    @FindBy(id = "password-confirmation")
    WebElement txtbx_confirmPassword;

    @FindBy(xpath = "//button[@title='Create an Account']")
    WebElement btn_createAccount;

    @FindBy(xpath = "//div[contains(@class,'success message')]//div[text()='Thank you for registering with Main Website Store.']")
    WebElement txt_successMessage;

    @FindBy(xpath = "//div[contains(@class,'error message')]//div[contains(text(),'There is already an account with this email address.')]")
    WebElement txt_existingUserMessage;

    @FindBy(xpath = "//div[contains(@id,'email_address-error') and contains(text(),'Please enter a valid email address (Ex: johndoe@domain.com).')]")
    WebElement txt_invalidEmailFormatMessage;

    public RegistrationPage() {
        this.driver = DriverFactory.getWebDriver();
        utils=new CommonUtilities(this.driver);
        PageFactory.initElements(this.driver, this);
        ExtentReportManager extentReporter= HookClass.getExtentObject();
    }

    public void enterPersonalInfoRegistrationDetails(String firstName, String lastName)
    {
        utils.sendKeys(txtbx_firstname,firstName,"First Name");
        utils.sendKeys(txtbx_lastname,lastName,"Last Name");
    }

    public void enterSignInRegistrationDetails(String email, String password)
    {
        utils.sendKeys(txtbx_email,email,"Email");
        utils.sendKeys(txtbx_password,password,"Password");
        utils.sendKeys(txtbx_confirmPassword,password,"Confirm Password");
    }

    public void submitRegistration()
    {
        utils.clickElement(btn_createAccount,"Create Account button");
    }

    public void verifySuccessfulRegistration()
    {
        utils.verifyIfElementPresent(txt_successMessage,"Successful Creation");
    }

    public void verifyAlreadyUsedEmail()
    {
        utils.verifyIfElementPresent(txt_existingUserMessage,"Already Used Email");
    }

    public void verifyInvalidEmailFormat()
    {
        utils.verifyIfElementPresent(txt_invalidEmailFormatMessage,"Invalid Email Format");
    }
}
