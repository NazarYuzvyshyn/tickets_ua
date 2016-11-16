package gdTicketsUaDesctopTest;

import gdTicketsUaDesctop.utils.Log;
import gdTicketsUaDesctop.utils.WebDriverFactory;
import org.testng.ITestContext;
import org.testng.annotations.*;

import static gdTicketsUaDesctop.utils.CommonServices.*;


/**
 * @author Назар on 04.11.2016.
 */
public class DefaultTestCase {

    public String testCaseName = this.getClass().toString().substring(this.getClass().toString().lastIndexOf(".") + 1);

    @BeforeSuite
    public void startTestSuite() {
        Log.info("=================================================================");
        Log.info("Test suite started");
        Log.info("=================================================================");
    }

    @Parameters("browser")
    @BeforeTest
    public void startTest(@Optional("ff") String browser) {

        Log.info("=================================================================");
        Log.info("TestCase: \"" + testCaseName + "\" started");
        Log.info("=================================================================");
        WebDriverFactory.setWebDriver(browser);
    }

    @AfterTest
    public void afterTest(ITestContext context) {
        if (context.getFailedTests().size() > 0 || context.getSkippedTests().size() > 0) {
            Log.error("TestCase: \"" + testCaseName + "\" FAILED.");
        }
        takeScreenshot(testCaseName, getUrl());
        clearCookies();
        WebDriverFactory.killDriver();
        Log.info("Browser has been closed");
        Log.info("=================================================================");
        Log.info("TestCase: \"" + testCaseName + "\" finished.");
        Log.info("=================================================================");
    }

    @AfterSuite
    public void finishTestSuite() {
        Log.info("=================================================================");
        Log.info("Test suite finished.");
        Log.info("=================================================================");
    }

}
