package gdTicketsUaDesctopTest;

import io.restassured.RestAssured;
import io.restassured.config.LogConfig;
import org.testng.annotations.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;

import static gdTicketsUaDesctop.utils.CommonServices.clearCookies;
import static gdTicketsUaDesctop.utils.CommonServices.getTestClassName;
import static gdTicketsUaDesctop.utils.WebDriverFactory.killDriver;
import static gdTicketsUaDesctop.utils.WebDriverFactory.setWebDriver;
import static gdTicketsUaDesctop.utils.loggers.AllureLoggerHandler.*;
import static gdTicketsUaDesctop.utils.loggers.Logger.info;
import static gdTicketsUaDesctop.utils.loggers.Logger.initLogFile;
import static io.restassured.RestAssured.config;


public class DefaultTestCase {

    private String testCaseName = getTestClassName(this.getClass().toString());
    String logFilePath;

    @BeforeSuite
    public void beforeSuite() {
        logFilePath = initLogFile();
        initAllureLoggerHandler(logFilePath);
    }

    @AfterSuite
    public void afterSuite() {
        closeLogFile();
    }

    @Parameters("browser")
    @BeforeTest
    public void startTest(@Optional("firefox") String browser) throws FileNotFoundException {
        info("========= * TestCase: \"" + testCaseName + "\" is started * =========");
        setWebDriver(browser);
    }

    @AfterTest
    public void afterTest() {
        clearCookies();
        killDriver();
        attachLogsToStep();
        info("Browser has been closed");
        info("========= * TestCase: \"" + testCaseName + "\" is finished * =========");
    }

}
