package gdTicketsUaDesctop.pages;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import java.time.LocalDate;
import java.util.List;
import static gdTicketsUaDesctop.utils.Log.info;
import static gdTicketsUaDesctop.utils.WaitFor.waitForUrl;
import static gdTicketsUaDesctop.utils.WebElementServices.*;

/**
 * @author Nazar on 03.11.2016.
 */
public class PurchasePage {

    public enum PayBy {
        CREDIT_CARD {
            String getString() {
                return "Банковской картой";
            }
        }, PRIVAT_24 {
            String getString() {
                return "Приват 24";
            }
        }, WEBMONEY {
            String getString() {
                return "Webmoney";
            }
        };

        abstract String getString();
    }

    private String price = "//*[@data-payment-data='tarif']";
    private String train = "//*[contains(@class,'one_offer-booking')]//strong";
    private String dateAndCity = "//*[contains(@class,'one_offer-booking')]//*[contains(@class,'departure-time')]";
    private String nameAndPlaceType = "//*[contains(@class,'segment')]//*[child::*[@data-label]]";
    private String submit = "//*[contains(@class,'booking_price_button')]//*[@type='submit']";


    public void payBy(PayBy type) {
        clickOn(type.getString(), "//*[text()='" + type.getString() + "']/..");
    }

    public boolean confirmTrain(String train) {
        List<WebElement> trains = getElements(this.train);
        return trains.stream().map((s) -> s.getAttribute("textContent"))
                .filter((s) -> s.contains(train.substring(0, 4)))
                .peek((s) -> info(s + " >- contains -< " + train))
                .findFirst().isPresent();
    }

    public boolean confirmDateAndCity(LocalDate date, String city) {
        String day = " " + date.getDayOfMonth() + " ";
        List<WebElement> datesCities = getElements(dateAndCity);
        return datesCities.stream().map(WebElement::getText)
                .filter((s) -> s.contains(day) && s.contains(city))
                .peek((s) -> info(s + " >- contains -< " + day + " " + city))
                .findFirst().isPresent();
    }

    public boolean confirmNameAndPlaceType(String name, String placeType) {
        List<WebElement> namesAndPlaces = getElements(nameAndPlaceType);
        return namesAndPlaces.stream().map(WebElement::getText)
                .filter((s) -> s.contains(name) && s.contains(placeType))
                .peek((s) -> info(s + " >- contains -< " + name + " " + placeType))
                .findFirst().isPresent();
    }

    /**
     * Method checks only forward price
     * @param price price of chosen ticket from result list
     * @return true if price before paying is equal to price of
     * chosen ticket on Result page
     */
    public boolean confirmPrice(String price) {
        String finalPrice = getPrice();
        info("Price of chosen ticket: " + price + " Price before paying: " + finalPrice);
        return clearPrice(finalPrice).equals(price);
    }

    /**
     * Method adds up prices of chosen tickets from result lists
     * and checks it with final price before paying
     * @param price forward ticket price
     * @param roundPrice backward ticket price
     * @return true if price before paying is equal to sum of prices of
     * chosen tickets on Result page
     */
    public boolean confirmPrice(String price, String roundPrice) {
        String clearPrice = clearPrice(price);
        String clearRoundPrice = clearPrice(roundPrice);
        String finalPrice = clearPrice(getPrice());
        Integer sum = Integer.parseInt(clearPrice) + Integer.parseInt(clearRoundPrice);
        String sumStr = String.valueOf(sum);
        info("Price of chosen tickets: " + sumStr + " Price before paying: " + finalPrice);
        return finalPrice.equals(sumStr);
    }

    private String clearPrice(String price) {
        return price.replaceAll("\\..+", "").replaceAll("[^\\d]", "");
    }

    private String getPrice() {
        WebElement priceElem = getElement(this.price);
        return priceElem.getAttribute("textContent");
    }

    public void submit() {
        clickOn("Продолжить", submit);
        waitForUrl("checkout");
    }

}
