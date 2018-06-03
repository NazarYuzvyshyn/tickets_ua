package gdTicketsUaDesctop.pages;

import gdTicketsUaDesctop.businessObjects.Ticket;
import gdTicketsUaDesctop.utils.loggers.AllureLoggerHandler;
import io.qameta.allure.Step;
import org.openqa.selenium.By;

import java.time.LocalDate;

import static gdTicketsUaDesctop.utils.WaitFor.*;
import static gdTicketsUaDesctop.utils.WebElementServices.clickOn;
import static gdTicketsUaDesctop.utils.WebElementServices.sendKeysWithEnter;
import static gdTicketsUaDesctop.utils.loggers.AllureLoggerHandler.attachLogsToStep;
import static gdTicketsUaDesctop.utils.loggers.AllureLoggerHandler.saveLogs;
import static gdTicketsUaDesctop.utils.loggers.Logger.info;
import static org.openqa.selenium.support.ui.ExpectedConditions.elementToBeClickable;

public class MainPage {

    private By from = By.xpath("//*[@id='from_name_as']");
    private By to = By.xpath("//*[@id='to_name']");
    private By round = By.xpath("//*[@id='round_trip']/following-sibling::*");
    private By forwardDate = By.xpath("//*[child::*[@id='departure_date']]");
    private By backwardDate = By.xpath("//*[child::*[@id='departure_date_back']]");
    private By calendar = By.xpath("//*[@id='ui-datepicker-div']");
    private By submit = By.xpath("//*[contains(@class,'search__form')]//*[@type='submit']");

    private Ticket ticket;
    private AllureLoggerHandler allureLogger;

    public MainPage(Ticket ticket) {
        this.ticket = ticket;
        this.allureLogger = allureLogger;
    }

    @Step("Try to get a forward date")
    public void getTicket() {
        info("--------- Departure point ---------");
        sendKeysWithEnter("Откуда", ticket.getForwardCity(), from);
        info("----------- Arrival point -----------");
        sendKeysWithEnter("Куда", ticket.getBackwardCity(), to);
        setDate(ticket.getForwardDate(), forwardDate);
        attachLogsToStep();
    }

    @Step("Try to get a backward date")
    public void getRoundTrip() {
        clickOn("'В обе стороны'", round);
        setDate(ticket.getBackwardDate(), backwardDate);
        attachLogsToStep();
    }

    private void setDate(LocalDate date, By dateField) {
        String day = String.valueOf(date.getDayOfMonth());
        String month = String.valueOf(date.getMonthValue() - 1);
        String year = String.valueOf(date.getYear());
        By xpath = By.xpath("//*[@data-year='" + year + "' and @data-month='" + month + "' and child::a[text()='" + day + "']]");
        waitCalendarDateEnable(dateField, xpath);
    }

    private void waitCalendarDateEnable(By datePickerLocator, By dayOfMonthLocator){
        clickOn("Date input", datePickerLocator);
        try{
            clickOn("Day of a month", dayOfMonthLocator, 2);
        }catch (Exception e){
            info(e.getMessage());
            info(">>>>>>>>>>>> Looks like the Date Picker disappeared, try again.");
            clickOn("Date input", datePickerLocator, 3);
            clickOn("Day of a month", dayOfMonthLocator, 2);
        }
    }

    @Step("Search Ticket")
    public void search() {
        clickOn("Поиск", submit);
        waitForUrl("results");
        attachLogsToStep();
    }

}
