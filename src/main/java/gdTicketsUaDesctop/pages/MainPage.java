package gdTicketsUaDesctop.pages;

import gdTicketsUaDesctop.businessObjects.Ticket;
import org.openqa.selenium.WebDriver;
import ru.yandex.qatools.allure.annotations.Step;

import java.time.LocalDate;

import static gdTicketsUaDesctop.utils.CommonServices.pressKey;
import static gdTicketsUaDesctop.utils.CommonServices.sleep;
import static gdTicketsUaDesctop.utils.Log.info;
import static gdTicketsUaDesctop.utils.WaitFor.*;
import static gdTicketsUaDesctop.utils.WebElementServices.clickOn;
import static gdTicketsUaDesctop.utils.WebElementServices.sendKeys;
import static gdTicketsUaDesctop.utils.WebElementServices.sendKeysWithEnter;
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

    @Step("Get Ticket")
    public void getTicket() {
        info("--------- Departure point ---------");
        sendKeysWithEnter("Откуда", ticket.getForwardCity(), from);
        info("----------- Arrival point -----------");
        sendKeysWithEnter("Куда", ticket.getBackwardCity(), to);
        setDate(ticket.getForwardDate(), forwardDate);
    }

    @Step("Get round Trip")
    public void getRoundTrip() {
        clickOn("'В обе стороны'", round);
        setDate(ticket.getBackwardDate(), backwardDate);
    }

    public void setDate(LocalDate date, String dateField){
        clickOn("", dateField);
        waitCondition(calendar, VISIBIL, 3);
        String day = String.valueOf(date.getDayOfMonth());
        String month = String.valueOf(date.getMonthValue() - 1);
        String year = String.valueOf(date.getYear());
        String xpath = "//*[@data-year='" + year + "' and @data-month='" + month + "']/*[text()='" + day + "']";
        clickOn(date.toString(), xpath);
    }

    public void search(){
        clickOn("Поиск", submit);
        waitForUrl("results");
    }

}
