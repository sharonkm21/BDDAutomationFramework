package TestRunner;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;

@CucumberOptions(
        features = "src/test/resources/Features/UserRegistration.feature",
        glue = {"StepDefinitions","Pages"},
        plugin = {"pretty",
                "html:target/cucumber-reports.html",
                "json:target/cucumber.json",
                "junit:target/cucumber.xml",
                "com.aventstack.extentreports.cucumber.adapter.ExtentCucumberAdapter:"},
        monochrome = true
)
public class RegistrationRunner extends AbstractTestNGCucumberTests
{
    /*@Parameters({"sheetName"})
    @BeforeClass
    public void setup(String sheetName) throws IOException {
        ExcelUtility excelUtils = new ExcelUtility();
        excelUtils.setExcelFile(GlobalVariables.TESTDATA_EXCEL_PATH, sheetName);
    }*/
}
