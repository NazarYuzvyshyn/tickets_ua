package gdTicketsUaDesctop.pages;

import gdTicketsUaDesctop.businessObjects.Ticket;
import org.openqa.selenium.WebDriver;

import static gdTicketsUaDesctop.utils.CommonServices.pressKey;
import static gdTicketsUaDesctop.utils.CommonServices.sleep;
import static gdTicketsUaDesctop.utils.Log.info;
import static gdTicketsUaDesctop.utils.WaitFor.*;
import static gdTicketsUaDesctop.utils.WebElementServices.clickOn;
import static gdTicketsUaDesctop.utils.WebElementServices.sendKeys;
import static org.openqa.selenium.Keys.ENTER;

/**
 * @author Nazar on 03.11.2016.
 */
public class MainPage {

    private String from = "//*[@id='from_name_as']";
    private String to = "//*[@id='to_name']";
    private String round = "//*[@id='round_trip']/following-sibling::*";
    private String forwardDate = "//*[@id='departure_date']";
    private String backwardDate = "//*[@id='departure_date_back']";
    private String calendar = "//*[@id='ui-datepicker-div']";
    private String submit = "//*[contains(@class,'main-search__block')]//*[@type='submit']";

    private WebDriver driver;
    private Ticket ticket;

    public MainPage(Ticket ticket, WebDriver driver) {
        this.driver = driver;
        this.ticket = ticket;
    }

    public void searchTicket() {
        inputCity("Откуда");
        inputDate("Дата Откуда", ticket.getForwardDate());
        clickOn("Поиск", submit);
        waitForUrl("results");
    }

    public void inputCity(String point) {
        if (point.contains("Откуда")) {
            info("--------- Departure point ---------");
            sendKeys(point, ticket.getForwardCity(), from);
        } else sendKeys(point, ticket.getBackwardCity(), to);
        sleep(1);
        pressKey(ENTER);
    }

    public void inputDate(String name, String date) {
        if (name.contains("Откуда")) clickOn(name, forwardDate);
        else clickOn(name, backwardDate);
        waitCondition(calendar, VISIBIL, 3);
        String day;
        if (date.startsWith("0")) {
            day = date.substring(1, 2);
        } else day = date.substring(0, 2);
        String month = date.substring(3, 5);
        String year = date.substring(6);
        String xpath = "//*[@data-year='" + year + "' and @data-month='" + month + "']/*[text()='" + day + "']";
        clickOn(date, xpath);
    }

    public void getRoundTrip() {
        clickOn("'В обе стороны'", round);
        info("----------- Arrival point -----------");
        inputCity("Куда");
        inputDate("Дата Куда", ticket.getBackwardDate());
    }
}
