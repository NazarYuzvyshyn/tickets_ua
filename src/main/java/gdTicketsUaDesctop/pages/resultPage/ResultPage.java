package gdTicketsUaDesctop.pages.resultPage;

import gdTicketsUaDesctop.businessObjects.Passenger;
import gdTicketsUaDesctop.businessObjects.Ticket;
import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.List;
import java.util.Random;

import static gdTicketsUaDesctop.utils.WaitFor.*;
import static gdTicketsUaDesctop.utils.WaitFor.WaitCondition.LIST_NOT_EMPTY;
import static gdTicketsUaDesctop.utils.WaitFor.WaitCondition.ENABLE;
import static gdTicketsUaDesctop.utils.WaitFor.WaitCondition.VISIBLE;
import static gdTicketsUaDesctop.utils.WebDriverFactory.getDriver;
import static gdTicketsUaDesctop.utils.WebElementServices.*;
import static gdTicketsUaDesctop.utils.loggers.AllureLoggerHandler.attachLogsToStep;
import static gdTicketsUaDesctop.utils.loggers.Logger.info;

/**
 * @author Nazar on 03.11.2016.
 */
public class ResultPage {

    private String typeName = "//*[contains(@class,'results__item-seats__type')]";
    private String trainCategories = "//*[contains(@class,'free-places') " +
            "and ancestor::*[@data-auto-controller='RailwayTrainController']]";
    private String freePlaces = "//*[contains(@class,'sits_block')]//a";

    private String trains = "//*[@data-auto-controller='RailwayTrainController']";
    private By firstName = By.xpath("//*[contains(@id,'first_name')]");
    private By lastName = By.xpath("//*[contains(@id,'last_name')]");
    private By phoneCode = By.xpath("//*[@id='buyer_data']//*[@id='phone_code']");
    private By phone = By.xpath("//*[@id='user_field[phone]']");
    private By ageType = By.xpath("//*[text()='Тип пассажира']/..//*[contains(@class,'chosen-single')]");
    private By buy = By.xpath("//*[@id='operation_type_3']");
    private By price = By.xpath("//*[contains(@class,'js-price-item-total')]");
    private By reserve = By.xpath("//*[@id='operation_type_1']");
    private By acceptOfferta = By.xpath("//*[@id='add-insurance']");
    private By submit = By.xpath("//*[contains(@class,'buy-block')]//*[@type='submit']");
    private By email = By.xpath("//*[@id='user_field[email]']");
    private By lastFirstNames = By.xpath("//*[@id='user_form']//*[@id='name']");

    private By drink = By.xpath("//*[@id='drink']");
    private By oneTea = By.xpath("//*[@id='services_service_5']");
    private By twoTea = By.xpath("//*[@id='services_service_6']");
    private By cargo = By.xpath("//*[@name='cargo']");
    private By noBed = By.xpath("//*[@id='bed']");

    private Passenger passenger;
    private Ticket ticket;
    private Random random = new Random();
    private String trainNumberAndName;

    public ResultPage(Passenger passenger, Ticket ticket) {
        this.passenger = passenger;
        this.ticket = ticket;
    }

    /**
     * Gets random train from list of results after search
     * and if trip is round write to Ticket object each one train name
     *
     * @return index of this train in result list
     */
    @Step("Get random train")
    public int getRandomTrain() {
        waitCondition(By.xpath(trains), LIST_NOT_EMPTY, 10);
        List<WebElement> listOfTrains = getElements(By.xpath(trains));
        int index = random.nextInt(listOfTrains.size());

        String trainNumberLocator = "(" + trains + ")" + "[" + index + "]" + "//strong";
        WebElement trainNumber = getElement(By.xpath(trainNumberLocator));
        String trainNumberAsText = getText(trainNumber);

        String trainNameLocator = trainNumberLocator + "/following-sibling::div";
        WebElement trainName = getElement(By.xpath(trainNameLocator));
        String trainNameAsText = getText(trainName);

        trainNumberAndName = trainNumberAsText + " " + trainNameAsText;
        ticket.setTrainNumberAndName(trainNumberAndName);

        info("--------------- Train -------------");
        info("Поезд: " + trainNumberAndName);
        attachLogsToStep();
        return index;
    }

    /**
     * Uses train list index (see above),chooses block of categories which is related to this train.
     * Can contains "Люкс","Купе","Плацкарт","Сидячий" (one of them or several)
     *
     * @param index index of train in result list
     */
    @Step("Get random place type")
    public void getRandomPlaceType(int index) {
        waitCondition(By.xpath(trainCategories), LIST_NOT_EMPTY, 10);

        String block = "(" + trainCategories + ")[" + (index + 1) + "]";
        By blockAsXpath = By.xpath(block + typeName);

        List<WebElement> typeNames = getElements(blockAsXpath);

        int typeIndex = random.nextInt(typeNames.size());
        String text = getText(typeNames.get(typeIndex));
        ticket.setPlaceType(text);

        String submitButton = block + typeName + "/..//a";
        clickOn("Тип: " + text, By.xpath(submitButton));
        attachLogsToStep();
    }

    /**
     * Gets available free place in chosen train and place category.
     */
    @Step("Get random place")
    public void getRandomPlace() {
        waitCondition(By.xpath(freePlaces), LIST_NOT_EMPTY, 10);
        List<WebElement> places = getElements(By.xpath(freePlaces));

        int placesIndex = random.nextInt(places.size());
        String text = getText(places.get(placesIndex));

        ticket.setPlaceNumber(text);

        String place = "(" + freePlaces + ")[" + (placesIndex + 1) + "]";
        clickOn("Место: " + text, By.xpath(place));
        attachLogsToStep();
    }

    /**
     * Fills first & last names
     */
    @Step("Fill passenger form")
    public void fillPassengerForm() {
        sendKeys("Фамилия", passenger.getLastName(), lastName);
        sendKeys("Имя", passenger.getFirstName(), firstName);
        ticket.setLastFirstNames(passenger.getLastName() + " " + passenger.getFirstName());
        attachLogsToStep();
    }

    private boolean checkDrink() {
        if (waitConditionAndReturnStatus(drink, VISIBLE, 5)) {
            clickOn("Напиток", drink);
        } else {
            info("There is no option 'Напиток' for this train ticket");
            return false;
        }
        return true;
    }

    public void checkOneTea() {
        if (checkDrink()) {
            waitCondition(oneTea, VISIBLE, 2);
            clickOn("1 чай", oneTea);
        } else info("There is no option '1 чай' for this train ticket");
    }

    public void checkTwoTea(){
        if (checkDrink()) {
            waitCondition(twoTea, VISIBLE, 2);
            clickOn("2 чая", twoTea);
        } else info("There is no option '2 чая' for this train ticket");
    }

    public void checkNoBed() {
        waitCondition(noBed, VISIBLE, 2);
        clickOn("Без постельного белья", noBed);
    }

    public void checkCargo() {
        waitCondition(cargo, VISIBLE, 2);
        clickOn("Дополнительный багаж ", cargo);
    }

    public void reserve() {
        waitCondition(reserve, ENABLE, 5);
        clickOn("Резервировать", reserve);
    }

    @Step("Submit")
    public void submit() {
        String text = getTextContent(getElement(price));
        ticket.setPrice(text);

        String currentUrl = getDriver().getCurrentUrl();
        clickOn("Продолжить", submit);
        attachLogsToStep();
        waitChangeUrl(currentUrl);
    }

    @Step("Fill contacts")
    public void fillContacts() {
        sendKeys("Email", passenger.getEmail(), email);
        sendKeys("Номер телефона", passenger.getPhone(), phone);
        attachLogsToStep();
    }

    public void acceptOfferta() {
        clickOn("Я принимаю правила публичной оферты", acceptOfferta);
    }

}

