package gdTicketsUaDesctop.utils;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import static gdTicketsUaDesctop.Constants.DRIVER_LOCATION;
import static gdTicketsUaDesctop.utils.CommonServices.moveToCoordinate;
import static org.testng.Assert.assertTrue;

/**
 * @author  Назар on 14.09.2016.
 */
public class WebDriverFactory {

    public static final ThreadLocal<WebDriver> DRIVER = new ThreadLocal<>();

    private WebDriverFactory(){}

    public static WebDriver getDriver() {
        return DRIVER.get();
    }

    public static void killDriver(){
        getDriver().quit();
        DRIVER.remove();
    }

    public static void setWebDriver(String browser) {
            switch (browser) {
                case "ff":
                    DRIVER.set(new FirefoxDriver());
                    Log.info("Used Firefox driver");
                    break;
                case "chrome":
                    WebDriverFactory.setChromeDriver(osVersion());
                    DRIVER.set(new ChromeDriver());
                    break;
            }
            getDriver().manage().window().maximize();
            moveToCoordinate(0, 0, getDriver());
            Log.info("Maximize window");
        }

    private static void setChromeDriver(String osName) {
        String path = "";
        if (osName.startsWith("win")) {
            path = DRIVER_LOCATION + "chrome/chrome-win/chromedriver.exe";
            Log.info("Used ChromeDriver for Windows");
        } else if (osName.startsWith("lin")) {
            path = DRIVER_LOCATION + "chrome/chrome-lin/chromedriver";
            Log.info("Used ChromeDriver for Linux");
        } else {
            Log.error("Your OS is invalid for ChromeDriver tests");
            assertTrue(false);
        }
        System.setProperty("webdriver.chrome.driver", path);
    }

    private static String osVersion(){
        return System.getProperty("os.name").toLowerCase();
    }

}
