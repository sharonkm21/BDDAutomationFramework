package StepDefinitions;

import BaseFactory.BaseTestClass;
import Report.ExtentReportManager;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import BankAccount.*;

import static StepDefinitions.HookClass.extentReporter;
import static org.testng.Assert.assertEquals;

public class WithdrawAmount extends BaseTestClass {

    private Account account;
    private double dispensedAmount;
    public ExtentReportManager extentReportManager=HookClass.getExtentObject();
    public int accountNumber;

    // Step 1: Given I have a balance of $100 in my account
    @Given("^I have a balance of \\$(\\d+) in my account$")
    public void setInitialBalance(int initialBalance) {
        account = new BankAccount(initialBalance);
        extentReporter.logInfo("Initial Balance: "+initialBalance);
    }

    // Step 2: When I request $20
    @When("^I request \\$(\\d+)$")
    public void setAmountRequested(int amountRequested) {
        try {
            account.withdraw(amountRequested);
            dispensedAmount = amountRequested;
            extentReporter.logPass("Amount dispensed : "+dispensedAmount,false,driver);
        } catch (IllegalArgumentException e) {
            dispensedAmount = 0;  // In case of insufficient funds
        }
    }

    // Step 3: Then $20 should be dispensed
    @Then("^\\$(\\d+) should be dispensed$")
    public void verifyAmountDispensed(int expectedDispensedAmount) {
        assertEquals(expectedDispensedAmount, dispensedAmount, 0);
        extentReporter.logPass("Dispensed Amount "+expectedDispensedAmount+" is verified.",false,driver);
    }
}
