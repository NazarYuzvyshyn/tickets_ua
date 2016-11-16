package gdTicketsUaDesctop.utils;

import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.testng.Assert;
import ru.yandex.qatools.allure.annotations.Attachment;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import static gdTicketsUaDesctop.utils.Log.info;
import static gdTicketsUaDesctop.utils.WebDriverFactory.getDriver;

/**
 * @author Назар on 14.09.2016.
 */
public class CommonServices {

    public static void assertThat(Boolean condition, String errorMessage ) {
        if (!condition){
            Log.error("ACTUAL RESULT: " + errorMessage);
        }
        Assert.assertTrue(condition);
    }

    public static void pressKey(Keys keys){
        Actions actions = new Actions(getDriver());
        actions.sendKeys(keys).perform();
        Log.info( "press " + keys.name());
    }

    public static void moveToCoordinate(int x, int y, WebDriver driver) {
        Actions actions = new Actions(driver);
        actions.moveByOffset(x, y).build().perform();
        info("Move to coordinate " + x + "x" + y);
    }

    public static void goTo(String url) {
        getDriver().get(url);
        Log.info("Go to: " + url);
        WaitFor.waitPageLoad(60);
    }

    public static void switchToNewWindow() {
        for (String win : getDriver().getWindowHandles()) {
            getDriver().switchTo().window(win);
        }
        info("Switch to new window");
        getDriver().manage().window().maximize();
        info("Maximize window");
    }

    public static void backPreviousPage(WebDriver driver) {
        driver.navigate().back();
    }

    public static void clearCookies(){
        getDriver().manage().deleteAllCookies();
        info("All cookies have been delete");
    }

    public static String getUrl(){
        return getDriver().getCurrentUrl();
    }

    public static void sleep(long sec){
        try {
            Thread.sleep(sec * 1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Attachment("{0} {1}")
    public static void takeScreenshot(String testCaseName, String message) {

        try {
            String screenshotName = testCaseName + "ScreenShot.png";
            final BufferedImage image;
            File scrFile = ((TakesScreenshot)getDriver()).getScreenshotAs(OutputType.FILE);
            image= ImageIO.read(scrFile);
            Graphics g = image.getGraphics();
            g.setFont(new Font("Arial Black", Font.PLAIN, 20));
            g.setColor(Color.DARK_GRAY);
            g.drawString("URL: " + message, 50, 100);
            g.dispose();

            ImageIO.write(image, "png", new File("target/screenshots/" + screenshotName));

            Log.info("");
            Log.warn("Screenshot captured.");
            Log.warn("Screenshot name: \"" + screenshotName + "\".");
        }
        catch (WebDriverException | IOException e){
            Log.error("Catch "+e);
        }

    }
}
