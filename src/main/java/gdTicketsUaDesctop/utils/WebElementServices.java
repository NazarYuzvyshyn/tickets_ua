package gdTicketsUaDesctop.utils;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import java.util.List;
import static gdTicketsUaDesctop.utils.CommonServices.pressKey;
import static gdTicketsUaDesctop.utils.CommonServices.sleep;
import static gdTicketsUaDesctop.utils.WaitFor.*;
import static gdTicketsUaDesctop.utils.WaitFor.WaitCondition.CLICKABLE;
import static gdTicketsUaDesctop.utils.WaitFor.WaitCondition.ENABLE;
import static gdTicketsUaDesctop.utils.WebDriverFactory.getDriver;
import static gdTicketsUaDesctop.utils.loggers.Logger.info;
import static org.openqa.selenium.Keys.ENTER;


/**
 * @author Назар on 14.09.2016.
 */
public class WebElementServices {

    public static void sendKeys(String name, String inputText, By locator) {
        waitCondition(locator, ENABLE, 15);
        getElement(locator).click();
        getElement(locator).clear();
        getElement(locator).sendKeys(inputText);
        info("\"" + inputText + " \" has been send to \"" + name + "\" " + locator);
    }

    public static void sendKeysWithEnter(String name, String inputText, By locator){
        sendKeys(name, inputText, locator);
        sleep(1);
        pressKey(ENTER);
    }

    public static void clickOn(String name, By locator, long timeout) {
        waitCondition(locator, CLICKABLE, timeout);
        info("Try to click on \"" + name + "\" " + locator);
        getElement(locator).click();
        info("Element has been clicked.");
    }

    public static void clickOn(String name, By locator){
        clickOn(name, locator, 10);
    }

    public static String getTextContent(WebElement element) {
        String textContent = element.getAttribute("textContent");
        info(String.format("textContent of element: '%s'", textContent));
        return textContent;
    }

    public static String getText(WebElement element){
        return element.getText();
    }

//    public static String getNormalizedSpacesText(By locator){
//        String xpath = locator.toString().replaceAll("By\\.\\w{1,10}:\\s", "");
//        xpath += "/normalize-space(text())";
//        By newLocator = By.xpath(xpath);
//        getDriver().findElement(newLocator);
//    }

    public static WebElement getElement(By locator) {
        return getDriver().findElement(locator);
    }

    public static List<WebElement> getElements(By locator){
        return getDriver().findElements(locator);
    }
}
