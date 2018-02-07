package gdTicketsUaDesctop.utils;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import static gdTicketsUaDesctop.utils.CommonServices.getUrl;
import static gdTicketsUaDesctop.utils.WebDriverFactory.getDriver;
import static gdTicketsUaDesctop.utils.WebElementServices.*;
import static org.openqa.selenium.support.ui.ExpectedConditions.*;
import static org.testng.Assert.assertTrue;

public class WaitFor {

    public enum WaitCondition{
        CLICKABLE,
        ENABLE,
        ELEMENT_ON_FOCUS,
        VISIBIL,
        PSESENCE,
        LIST_NOT_EMPTY
    }

    public static void waitCondition(String locator, WaitCondition waitFor, long timeout) {
        WebDriverWait wait = new WebDriverWait(getDriver(), timeout);
        try {
            switch (waitFor) {
                case CLICKABLE:
                    wait.until((WebDriver webDriver) -> elementToBeClickable(By.xpath(locator)));
                    break;
                case LIST_NOT_EMPTY:
                    wait.until((WebDriver webDriver) -> !getElements(locator).isEmpty());
                    break;
                case ENABLE:
                    wait.until((WebDriver webDriver) -> getElement(locator).isEnabled());
                    break;
                case PSESENCE:
                    wait.until((WebDriver webDriver) -> presenceOfElementLocated(By.xpath(locator)));
                    break;
                case VISIBIL:
                    wait.until((WebDriver webDriver) -> visibilityOfElementLocated(By.xpath(locator)));
                    break;
                case ELEMENT_ON_FOCUS:
                    clickOn("Make focus on element", locator);
            }
        } catch (TimeoutException e) {
            Log.error(locator + " isn't " + waitFor.name());
            throw new TimeoutException(e);
        }
    }

    public static void waitChangeUrl(String staleUrl) {
        try {
            WebDriverWait wait = new WebDriverWait(getDriver(), 30);
            wait.until((WebDriver webDriver) -> !getUrl().equals(staleUrl));
            waitPageLoad(60);
        } catch (TimeoutException e) {
            Log.error("<< Stale URL >> : " + staleUrl + " << Current URL >> : " + getUrl());
            assertTrue(false);
        }
    }

    public static void waitForUrl(String newUrl) {
        try {
            WebDriverWait wait = new WebDriverWait(getDriver(), 30);
            wait.until((WebDriver webDriver) -> getUrl().contains(newUrl));
            waitPageLoad(60);
        } catch (TimeoutException e) {
            Log.error(" << Current URL >> : " + getUrl() + " dosn't contains : " + newUrl);
            assertTrue(false);
        }
    }

    public static void waitPageLoad(long timeout) {
        try {
            WebDriverWait wait = new WebDriverWait(getDriver(), timeout);
            wait.until((WebDriver webDriver) -> ((JavascriptExecutor) getDriver()).
                    executeScript("return document.readyState").equals("complete"));
        } catch (TimeoutException e) {
            Log.error("Page hasn't been loaded in " + timeout + " seconds");
            assertTrue(false);
        }
    }

}
