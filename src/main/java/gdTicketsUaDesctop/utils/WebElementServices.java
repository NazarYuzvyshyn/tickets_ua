package gdTicketsUaDesctop.utils;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.List;

import static gdTicketsUaDesctop.utils.CommonServices.pressKey;
import static gdTicketsUaDesctop.utils.CommonServices.sleep;
import static gdTicketsUaDesctop.utils.Log.info;
import static gdTicketsUaDesctop.utils.WaitFor.*;
import static gdTicketsUaDesctop.utils.WebDriverFactory.getDriver;
import static org.openqa.selenium.Keys.ENTER;


/**
 * @author Назар on 14.09.2016.
 */
public class WebElementServices {

    public static void sendKeys(String name, String inputText, String locator) {
        waitCondition(locator, ENABLE, 15);
        getElement(locator).click();
        getElement(locator).sendKeys(inputText);
        info("\"" + inputText + " \" has been send to \"" + name + "\" " + locator);
    }

    public static void sendKeysWithEnter(String name, String inputText, String locator){
        sendKeys(name, inputText, locator);
        sleep(1);
        pressKey(ENTER);
    }

    public static void clickOn(String name, String locator) {
        waitCondition(locator, CLICKABLE, 15);
        getElement(locator).click();
        info("Click on \"" + name + "\" " + locator);
    }

    public static String getTextContent(WebElement element) {
        return element.getAttribute("textContent");
    }

    public static WebElement getElement(String locator) {
        return getDriver().findElement(By.xpath(locator));
    }

    public static List<WebElement> getElements(String locator){
        return getDriver().findElements(By.xpath(locator));
    }
}
