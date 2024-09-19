package StepDefinitions;

import Pages.SignInPage;
import Utility.CommonUtilities;
import Utility.ExcelUtility;
import Utility.GlobalVariables;
import WebDriverFactory.DriverFactory;
import io.cucumber.java.BeforeAll;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.openqa.selenium.WebDriver;

import java.io.IOException;

public class SignInOutSteps
{
    WebDriver driver;
    SignInPage signInPage;
    CommonUtilities utils = new CommonUtilities(DriverFactory.getWebDriver());

    public SignInOutSteps() {
        this.signInPage = new SignInPage(driver);
    }

    @BeforeAll
    public static void setUp() throws IOException
    {
        ExcelUtility excelUtils = new ExcelUtility();
        excelUtils.setExcelFile(GlobalVariables.TESTDATA_EXCEL_PATH, "SignInFeature");
    }

    @Given("I am on the sign-in page")
    public void iAmOnTheSignInPage()
    {
        utils.navigateTo("https://magento.softwaretestingboard.com/customer/account/login");
    }

    @When("I enter {string} and {string} for sign-in")
    public void iEnterAndForSignIn(String emailKey, String passwordKey) {
        String username=utils.readProperty(emailKey);
        String password=utils.readProperty(passwordKey);
        signInPage.enterLoginDetails(username,password);
    }

    @And("I click the sign-in button")
    public void iClickTheSignInButton() {
        signInPage.clickSignIn();
    }

    @Then("I should be signed in successfully")
    public void iShouldBeSignedInSuccessfully() {
        signInPage.verifySuccessLogin();
    }

    @Then("I should see an error message about invalid password")
    public void iShouldSeeAnErrorMessageAboutInvalidCredentials() {
        signInPage.verifyInvalidLogin();
    }

    @When("I click the sign-out button")
    public void iClickTheSignOutButton() {
        signInPage.signOutFromAccount();
    }

    @Then("I should be signed out successfully")
    public void iShouldBeSignedOutSuccessfully() {
        signInPage.verifyLogoutSuccessful();
    }

    @Then("I should see an error message about invalid username")
    public void iShouldSeeAnErrorMessageAboutInvalidUsername()
    {

    }
}
