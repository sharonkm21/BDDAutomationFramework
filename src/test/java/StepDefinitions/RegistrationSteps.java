package StepDefinitions;

import BaseFactory.BaseTestClass;
import Pages.RegistrationPage;
import Utility.CommonUtilities;
import Utility.ExcelUtility;
import Utility.GlobalVariables;
import io.cucumber.java.BeforeAll;
import WebDriverFactory.DriverFactory;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.openqa.selenium.WebDriver;

import java.io.IOException;

public class RegistrationSteps extends BaseTestClass
{
    WebDriver driver;
    RegistrationPage registrationPage;
    CommonUtilities utils =new CommonUtilities(DriverFactory.getWebDriver());

    public RegistrationSteps() {
        this.registrationPage = new RegistrationPage();
    }

    @BeforeAll
    public static void setUp() throws IOException
    {
        ExcelUtility excelUtils = new ExcelUtility();
        excelUtils.setExcelFile(GlobalVariables.TESTDATA_EXCEL_PATH, "RegistrationFeature");
    }

    @Given("I am on the account creation page")
    public void i_am_on_the_account_creation_page()
    {
        utils.navigateTo("https://magento.softwaretestingboard.com/customer/account/create/");
    }

    @When("I enter {string} and {string} for Personal Information")
    public void i_enter_and_for_personal_information(String firstNameKey, String lastNameKey) {
        String firstName= utils.readProperty(firstNameKey);
        String lastName= utils.readProperty(lastNameKey);
        registrationPage.enterPersonalInfoRegistrationDetails(firstName,lastName);
    }

    @When("I enter {string} and {string} for Sign in Information")
    public void i_enter_and_for_sign_in_information(String emailKey, String passwordKey) {
        String email = utils.readProperty(emailKey);
        String password = utils.readProperty(passwordKey);
        registrationPage.enterSignInRegistrationDetails(email,password);
    }

    @And("I submit the registration form")
    public void i_submit_the_registration_form() {
        registrationPage.submitRegistration();
    }

    @Then("I should be registered successfully")
    public void i_should_be_registered_successfully()
    {
        registrationPage.verifySuccessfulRegistration();
    }

    @Then("I should see an error message about email already in use")
    public void i_should_see_an_error_message_about_email_already_in_use()
    {
        registrationPage.verifyAlreadyUsedEmail();
    }

    @Then("I should see an error message about invalid email format")
    public void i_should_see_an_error_message_about_invalid_email_format()
    {
        registrationPage.verifyInvalidEmailFormat();
    }

}