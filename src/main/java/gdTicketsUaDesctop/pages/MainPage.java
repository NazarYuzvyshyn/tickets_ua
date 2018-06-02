package gdTicketsUaDesctop.pages;
import gdTicketsUaDesctop.businessObjects.Ticket;
import io.qameta.allure.Step;
import org.openqa.selenium.By;

import java.time.LocalDate;
import static gdTicketsUaDesctop.utils.Log.info;
import static gdTicketsUaDesctop.utils.WaitFor.*;
import static gdTicketsUaDesctop.utils.WaitFor.WaitCondition.VISIBLE;
import static gdTicketsUaDesctop.utils.WebElementServices.clickOn;
import static gdTicketsUaDesctop.utils.WebElementServices.sendKeysWithEnter;

public class MainPage {

    private By from = By.xpath("//*[@id='from_name_as']");
    private By to = By.xpath("//*[@id='to_name']");
    private By round = By.xpath("//*[@id='round_trip']/following-sibling::*");
    private By forwardDate = By.xpath("//*[child::*[@id='departure_date']]");
    private By backwardDate = By.xpath("//*[@id='departure_date_back']");
    private By calendar = By.xpath("//*[@id='ui-datepicker-div']");
    private By submit = By.xpath("//*[contains(@class,'main-search__block')]//*[@type='submit']");

    private Ticket ticket;

    public MainPage(Ticket ticket) {
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

    public void getRoundTrip() {
        clickOn("'В обе стороны'", round);
        setDate(ticket.getBackwardDate(), backwardDate);
    }

    private void setDate(LocalDate date, By dateField){
        clickOn("", dateField);
        waitCondition(calendar, VISIBLE, 3);
        String day = String.valueOf(date.getDayOfMonth());
        String month = String.valueOf(date.getMonthValue() - 1);
        String year = String.valueOf(date.getYear());
        By xpath = By.xpath("//*[@data-year='" + year + "' and @data-month='" + month + "']/*[text()='" + day + "']");
        clickOn(date.toString(), xpath);
    }

    public void search(){
        clickOn("Поиск", submit);
        waitForUrl("results");
    }

}
