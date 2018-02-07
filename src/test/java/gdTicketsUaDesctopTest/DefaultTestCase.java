package gdTicketsUaDesctopTest;

import gdTicketsUaDesctop.utils.Log;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;

import static gdTicketsUaDesctop.utils.CommonServices.clearCookies;
import static gdTicketsUaDesctop.utils.CommonServices.getTestClassName;
import static gdTicketsUaDesctop.utils.WebDriverFactory.killDriver;
import static gdTicketsUaDesctop.utils.WebDriverFactory.setWebDriver;

/**
 * @author Назар on 04.11.2016.
 */
public class DefaultTestCase {

    private String testCaseName = getTestClassName(this.getClass().toString());

    @Parameters("browser")
    @BeforeTest
    public void startTest(@Optional("ff") String browser) {
        Log.info("========= * TestCase: \"" + testCaseName + "\" is started * =========");
        setWebDriver(browser);
    }

    @AfterTest
    public void afterTest() {
        clearCookies();
        killDriver();
        Log.info("Browser has been closed");
        Log.info("========= * TestCase: \"" + testCaseName + "\" is finished * =========");
    }

}
