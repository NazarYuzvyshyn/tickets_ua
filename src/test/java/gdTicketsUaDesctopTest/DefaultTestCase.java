package gdTicketsUaDesctopTest;

import org.testng.annotations.*;

import static gdTicketsUaDesctop.utils.CommonServices.clearCookies;
import static gdTicketsUaDesctop.utils.CommonServices.getTestClassName;
import static gdTicketsUaDesctop.utils.WebDriverFactory.killDriver;
import static gdTicketsUaDesctop.utils.WebDriverFactory.setWebDriver;
import static gdTicketsUaDesctop.utils.loggers.AllureLoggerHandler.*;
import static gdTicketsUaDesctop.utils.loggers.Logger.info;
import static gdTicketsUaDesctop.utils.loggers.Logger.initLogFile;


public class DefaultTestCase {

    private String testCaseName = getTestClassName(this.getClass().toString());

    @BeforeSuite
    public void beforeSuite() {
        String logFilePath = initLogFile();
        initAllureLoggerHandler(logFilePath);
    }

    @AfterSuite
    public void afterSuite() {
        closeLogFile();
    }

    @Parameters("browser")
    @BeforeTest
    public void startTest(@Optional("firefox") String browser) {
        info("========= * TestCase: \"" + testCaseName + "\" is started * =========");
        setWebDriver(browser);
        attachLogsToStep();
    }

    @AfterTest
    public void afterTest() {
        clearCookies();
        killDriver();
        info("Browser has been closed");
        info("========= * TestCase: \"" + testCaseName + "\" is finished * =========");
    }

}
