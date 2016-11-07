package gdTicketsUaDesctop.pages;

import gdTicketsUaDesctop.businessObjects.Passenger;
import gdTicketsUaDesctop.businessObjects.Ticket;
import gdTicketsUaDesctop.utils.Log;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;
import java.util.Random;

import static gdTicketsUaDesctop.utils.CommonServices.sleep;
import static gdTicketsUaDesctop.utils.Log.info;
import static gdTicketsUaDesctop.utils.WaitFor.*;
import static gdTicketsUaDesctop.utils.WebDriverFactory.getDriver;
import static gdTicketsUaDesctop.utils.WebElementServices.*;
import static jdk.nashorn.internal.runtime.regexp.joni.Config.log;

/**
 * @author Nazar on 03.11.2016.
 */
public class ResultPage {

    private String trains = "//*[contains(@class,'train') and not(contains(@class,'your'))]/strong";
    private String trainCategories = "//*[contains(@class,'free-places') and preceding-sibling::*[contains(@class,'train')]]";
    private String typeName = "//span";
    private String freePlaces = "//*[contains(@class,'sits_block')]//a";
    private String firstName = "//*[contains(@id,'firstname')]";
    private String lastName = "//*[contains(@id,'lastname')]";
    private String phoneCode = "//*[@id='buyer_data']//*[@id='phone_code']";
    private String phone = "//*[@id='buyer_data']//*[@id='phone_number']";
    private String ageType = "//*[text()='Тип пассажира']/..//*[contains(@class,'chosen-single')]";
    private String oneTea = "//*[@id='services_service_2']";
    private String twoTea = "//*[@id='services_service_3']";
    private String cargo = "//*[@name='cargo']";
    private String noBed = "//*[@id='bed']";
    private String buy = "//*[@id='operation_type_3']";
    private String price = "//*[contains(@class,'buy-block')]//strong[not(contains(@class,'old-price'))]";
    private String reserve = "//*[@id='operation_type_1']";
    private String acceptOfferta = "//*[@id='i_accept_offerta']/..";
    private String submit = "//*[contains(@class,'buy-block')]//*[@type='submit']";
    private String email = "//*[@id='user_form']//*[@id='email']";
    private String lastFirstNames = "//*[@id='user_form']//*[@id='name']";

    private WebDriver driver;
    private Passenger passenger;
    private Ticket ticket;
    private int counter = 0;
    private Random random = new Random();

    public ResultPage(Passenger passenger, Ticket ticket, WebDriver driver) {
        this.driver = driver;
        this.passenger = passenger;
        this.ticket = ticket;
    }

    /**
     * Gets random train from list of results after search
     * and if trip is round write to Ticket object each one train name
     * @return index of this train in result list
     */
    public int getRandomTrain() {
        waitCondition(trains, LIST_NOT_EMPTY, 10);
        List<WebElement> listOfTrains = getElements(trains);
        int index = random.nextInt(listOfTrains.size());
        String text = getTextContent(listOfTrains.get(index));
        info("--------------- Train -------------");
        info("Поезд: " + text);
        if (counter == 0)ticket.trainNumber = text;
        else ticket.trainNumberRound = text;
        return index;
    }

    /**
     * Uses train list index (see above),chooses block of categories which is related to this train.
     * Can contains "Люкс","Купе","Плацкарт","Сидячий" (one of them or several)
     * @param index index of train in result list
     */
    public void getRandomPlaceType(int index) {
        String block = "(" + trainCategories + ")[" + (index + 1) + "]";
        waitCondition(trainCategories, LIST_NOT_EMPTY, 10);
        List<WebElement> typeNames = getElements(block + typeName);
        int typeIndex = random.nextInt(typeNames.size());
        String text = getTextContent(typeNames.get(typeIndex));
        String chooseType = "(" + block + typeName + "/../a)[" + (typeIndex + 1) + "]";
        clickOn("Тип: " + text, chooseType);
        if (counter == 0)ticket.placeType = text;
        else ticket.placeTypeRound = text;
    }

    /**
     * Gets available free place in chosen train and place category.
     */
    public void getRandomPlace() {
        waitCondition(freePlaces, LIST_NOT_EMPTY, 10);
        List<WebElement> places = getElements(freePlaces);
        int placesIndex = random.nextInt(places.size());
        String text = getTextContent(places.get(placesIndex));
        String place = "(" + freePlaces + ")[" + (placesIndex + 1) + "]";
        clickOn("Место: " + text, place);
        if (counter ==0)ticket.placeNumber = text;
        else ticket.placeNumberRound = text;
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
        if (this.passenger.getNoBed().equals("true")) clickOn("Без постельного белья", noBed);
        if (this.passenger.getCargo().equals("true")) clickOn("Дополнительный багаж ", cargo);
        if (this.passenger.getTea().equals("1")) clickOn("Чай", oneTea);
        if (this.passenger.getTea().equals("2")) clickOn("2 чая", twoTea);
    }

    public void reserve() {
        waitCondition(reserve, ENABLE, 5);
        clickOn("Резервировать", reserve);
    }

    public void submit() {
        String text = getTextContent(getElement(price));
        if (counter == 0)ticket.price = text;
        else ticket.priceRound = text;
        counter = 1;
        String currentUrl = getDriver().getCurrentUrl();
        Log.info("-----------------------------------");
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

