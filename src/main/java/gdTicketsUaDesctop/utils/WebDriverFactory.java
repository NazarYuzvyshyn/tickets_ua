package gdTicketsUaDesctop.utils;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import static gdTicketsUaDesctop.utils.TestFailure.failTest;

import java.net.MalformedURLException;
import java.net.URI;
import static gdTicketsUaDesctop.utils.CommonServices.moveToCoordinate;
import java.time.LocalDateTime;

/**
 * @author Назар on 14.09.2016.
 */
public class WebDriverFactory {

    private static final ThreadLocal<WebDriver> DRIVER = new ThreadLocal<>();

    private WebDriverFactory() {
    }

    public static WebDriver getDriver() {
        return DRIVER.get();
    }

    public static void killDriver() {
        getDriver().quit();
        DRIVER.remove();
    }

    public static void setWebDriver(String browser) {
        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setBrowserName(browser);
//        capabilities.setCapability("enableVNC", true);
        capabilities.setCapability("enableVideo", true);
        capabilities.setCapability("videoName", LocalDateTime.now().toString());
        capabilities.setCapability("videoScreenSize", "1366x768");

        try {
            DRIVER.set(new RemoteWebDriver(URI.create("http://localhost:4444/wd/hub").toURL(),
                    capabilities
            ));
        } catch (MalformedURLException e) {
            failTest(e.getMessage());
        }
        moveToCoordinate(0, 0, getDriver());
//        AllureLoggerHandler.info("Maximized window");
    }

    private static String osVersion() {
        return System.getProperty("os.name").toLowerCase();
    }

}
