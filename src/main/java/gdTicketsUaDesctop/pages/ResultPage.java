package gdTicketsUaDesctop.pages;

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
import static gdTicketsUaDesctop.utils.loggers.AllureLoggerHandler.saveLogs;
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
    private By drink = By.xpath("//*[@id='drink']");
    private By oneTea = By.xpath("//*[@id='services_service_5']");
    private By twoTea = By.xpath("//*[@id='services_service_6']");
    private By cargo = By.xpath("//*[@name='cargo']");
    private By noBed = By.xpath("//*[@id='bed']");
    private By buy = By.xpath("//*[@id='operation_type_3']");
    private By price = By.xpath("//*[contains(@class,'js-price-item-total')]");
    private By reserve = By.xpath("//*[@id='operation_type_1']");
    private By acceptOfferta = By.xpath("//*[@id='add-insurance']");
    private By submit = By.xpath("//*[contains(@class,'buy-block')]//*[@type='submit']");
    private By email = By.xpath("//*[@id='user_field[email]']");
    private By lastFirstNames = By.xpath("//*[@id='user_form']//*[@id='name']");

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
     * @return index of this train in result list
     */
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
        info("--------------- Train -------------");
        info("Поезд: " + trainNumberAndName);
        return index;
    }

    @Step("Get backward random train")
    public int getBackwardRandomTrain() {
        int randomTrainIndex = getRandomTrain();
        ticket.trainNumberRound = trainNumberAndName;
        attachLogsToStep();
        return randomTrainIndex;
    }

    @Step("Get forward random train")
    public int getForwardRandomTrain() {
        int randomTrainIndex = getRandomTrain();
        ticket.trainNumber = trainNumberAndName;
        attachLogsToStep();
        return randomTrainIndex;
    }

    /**
     * Uses train list index (see above),chooses block of categories which is related to this train.
     * Can contains "Люкс","Купе","Плацкарт","Сидячий" (one of them or several)
     * @param index index of train in result list
     */
    public void getRandomPlaceType(int index) {
        waitCondition(By.xpath(trainCategories), LIST_NOT_EMPTY, 10);

        String block = "(" + trainCategories + ")[" + (index + 1) + "]";
        By blockAsXpath = By.xpath(block + typeName);

        List<WebElement> typeNames = getElements(blockAsXpath);

        int typeIndex = random.nextInt(typeNames.size());
        String text = getText(typeNames.get(typeIndex));

        String submitButton = block + typeName + "/..//a";
        clickOn("Тип: " + text, By.xpath(submitButton));

//        if (counter == 0)ticket.placeType = text;
//        else ticket.placeTypeRound = text;
    }

    /**
     * Gets available free place in chosen train and place category.
     */
    public void getRandomPlace() {
        waitCondition(By.xpath(freePlaces), LIST_NOT_EMPTY, 10);
        List<WebElement> places = getElements(By.xpath(freePlaces));

        int placesIndex = random.nextInt(places.size());
        String text = getText(places.get(placesIndex));

        String place = "(" + freePlaces + ")[" + (placesIndex + 1) + "]";
        clickOn("Место: " + text, By.xpath(place));
//        if (counter ==0)ticket.placeNumber = text;
//        else ticket.placeNumberRound = text;
    }

    /**
     * Fills first & last names
     */
    public void fillPassengerForm() {
        sendKeys("Фамилия", passenger.getLastName(), lastName);
        sendKeys("Имя", passenger.getFirstName(), firstName);
        ticket.lastFirstNames = passenger.getLastName() + " " + passenger.getFirstName();
    }

    public void extraServices() {
        if (this.passenger.getNoBed().equals("true") && waitConditionAndReturnStatus(noBed, VISIBLE, 2)) {
            clickOn("Без постельного белья", noBed);
        }
        if (this.passenger.getCargo().equals("true") && waitConditionAndReturnStatus(cargo, VISIBLE, 2)){
            clickOn("Дополнительный багаж ", cargo);
        }
        if (this.passenger.getTea().equals("1") && waitConditionAndReturnStatus(drink, VISIBLE, 2)) {
            clickOn("Напиток",drink);
            if (waitConditionAndReturnStatus(oneTea, VISIBLE, 2))
                clickOn("Чай", oneTea);
        }
        if (this.passenger.getTea().equals("2") && waitConditionAndReturnStatus(drink, VISIBLE, 2)) {
            clickOn("Напиток",drink);
            if (waitConditionAndReturnStatus(twoTea, VISIBLE, 2))
                clickOn("2 чая", twoTea);
        }
    }

    public void reserve() {
        waitCondition(reserve, ENABLE, 5);
        clickOn("Резервировать", reserve);
    }

    public void submit() {
//        String text = getTextContent(getElement(price));
//        if (counter == 0)ticket.price = text;
//        else ticket.priceRound = text;
//        counter = 1;
        String currentUrl = getDriver().getCurrentUrl();
        info("-----------------------------------");
        clickOn("Продолжить", submit);
        waitChangeUrl(currentUrl);
    }

    public void fillContacts() {
        sendKeys("Email", passenger.getEmail(), email);
        sendKeys("Номер телефона", passenger.getPhone(), phone);
    }

    public void acceptOfferta() {
        clickOn("Я принимаю правила публичной оферты", acceptOfferta);
    }

}

