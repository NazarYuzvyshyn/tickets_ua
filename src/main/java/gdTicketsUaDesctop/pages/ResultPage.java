package gdTicketsUaDesctop.pages;

import gdTicketsUaDesctop.businessObjects.Passenger;
import gdTicketsUaDesctop.businessObjects.Ticket;
import gdTicketsUaDesctop.utils.Log;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import java.util.List;
import java.util.Random;
import static gdTicketsUaDesctop.utils.Log.info;
import static gdTicketsUaDesctop.utils.WaitFor.*;
import static gdTicketsUaDesctop.utils.WaitFor.WaitCondition.LIST_NOT_EMPTY;
import static gdTicketsUaDesctop.utils.WaitFor.WaitCondition.ENABLE;
import static gdTicketsUaDesctop.utils.WebDriverFactory.getDriver;
import static gdTicketsUaDesctop.utils.WebElementServices.*;

/**
 * @author Nazar on 03.11.2016.
 */
public class ResultPage {

    private String typeName = "//span";
    private String trainCategories = "//*[contains(@class,'free-places') and preceding-sibling::*[contains(@class,'train')]]";
    private String freePlaces = "//*[contains(@class,'sits_block')]//a";

    private By trains = By.xpath("//*[contains(@class,'train') and not(contains(@class,'your'))]/strong");
    private By firstName = By.xpath("//*[contains(@id,'firstname')]");
    private By lastName = By.xpath("//*[contains(@id,'lastname')]");
    private By phoneCode = By.xpath("//*[@id='buyer_data']//*[@id='phone_code']");
    private By phone = By.xpath("//*[@id='buyer_data']//*[@id='phone_number']");
    private By ageType = By.xpath("//*[text()='Тип пассажира']/..//*[contains(@class,'chosen-single')]");
    private By oneTea = By.xpath("//*[@id='services_service_2']");
    private By twoTea = By.xpath("//*[@id='services_service_3']");
    private By cargo = By.xpath("//*[@name='cargo']");
    private By noBed = By.xpath("//*[@id='bed']");
    private By buy = By.xpath("//*[@id='operation_type_3']");
    private By price = By.xpath("//*[contains(@class,'buy-block')]//strong[not(contains(@class,'old-price'))]");
    private By reserve = By.xpath("//*[@id='operation_type_1']");
    private By acceptOfferta = By.xpath("//*[@id='i_accept_offerta']/..");
    private By submit = By.xpath("//*[contains(@class,'buy-block')]//*[@type='submit']");
    private By email = By.xpath("//*[@id='user_form']//*[@id='email']");
    private By lastFirstNames = By.xpath("//*[@id='user_form']//*[@id='name']");

    private Passenger passenger;
    private Ticket ticket;
    private int counter = 0;
    private Random random = new Random();

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
        waitCondition(By.xpath(trainCategories), LIST_NOT_EMPTY, 10);

        String block = "(" + trainCategories + ")[" + (index + 1) + "]";
        By blockAsXpath = By.xpath(block + typeName);

        List<WebElement> typeNames = getElements(blockAsXpath);

        int typeIndex = random.nextInt(typeNames.size());
        String text = getTextContent(typeNames.get(typeIndex));

        String chooseType = "(" + block + typeName + "/../a)[" + (typeIndex + 1) + "]";
        clickOn("Тип: " + text, By.xpath(chooseType));

        if (counter == 0)ticket.placeType = text;
        else ticket.placeTypeRound = text;
        throw new RuntimeException();
    }

    /**
     * Gets available free place in chosen train and place category.
     */
    public void getRandomPlace() {
        waitCondition(By.xpath(freePlaces), LIST_NOT_EMPTY, 10);
        List<WebElement> places = getElements(By.xpath(freePlaces));

        int placesIndex = random.nextInt(places.size());
        String text = getTextContent(places.get(placesIndex));

        String place = "(" + freePlaces + ")[" + (placesIndex + 1) + "]";
        clickOn("Место: " + text, By.xpath(place));
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

