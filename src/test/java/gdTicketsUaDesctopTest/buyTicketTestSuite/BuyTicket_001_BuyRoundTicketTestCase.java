package gdTicketsUaDesctopTest.buyTicketTestSuite;

import gdTicketsUaDesctop.businessObjects.Passenger;
import gdTicketsUaDesctop.businessObjects.Ticket;
import gdTicketsUaDesctop.pages.MainPage;
import gdTicketsUaDesctop.pages.PurchasePage;
import gdTicketsUaDesctop.pages.ResultPage;
import gdTicketsUaDesctop.utils.PropertyReader;
import gdTicketsUaDesctop.utils.TestListener;
import gdTicketsUaDesctopTest.DefaultTestCase;
import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import static gdTicketsUaDesctop.Constants.BASE_URL;
import static gdTicketsUaDesctop.Constants.LIQPAY;
import static gdTicketsUaDesctop.Constants.PROPERTY_LOCATION;
import static gdTicketsUaDesctop.pages.PurchasePage.PayBy.PRIVAT_24;
import static gdTicketsUaDesctop.utils.CommonServices.assertThat;
import static gdTicketsUaDesctop.utils.CommonServices.getUrl;
import static gdTicketsUaDesctop.utils.CommonServices.goTo;
import static org.testng.Assert.assertTrue;

/**
 * @author Nazar on 04.11.2016.
 */
@Listeners({TestListener.class})
@Feature("Buy Tickets Feature")
public class BuyTicket_001_BuyRoundTicketTestCase extends DefaultTestCase {

    private Ticket ticket = new Ticket("buyRound.properties", "random");
    private MainPage mainPage = new MainPage(ticket);
    private ResultPage resultPage;
    private PurchasePage purchasePage = new PurchasePage();

    @BeforeTest
    public void before(){
        PropertyReader propertyReader = new PropertyReader(
                PROPERTY_LOCATION + "passenger/adult.properties");
        Passenger pass = new Passenger.PassengerBuilder().
                withFirstName(propertyReader.getValue("firstName")).
                withLastName(propertyReader.getValue("lastName")).
                withEmail(propertyReader.getValue("email")).
                withPhone(propertyReader.getValue("phone")).
                build();
        resultPage = new ResultPage(pass,ticket);
    }



    @Test
    @Description("Buy Round Back Ticket")
    public void BuyTicket_001_BuyRoundTicket() {

        goTo(BASE_URL);

        mainPage.getTicket();
        mainPage.getRoundTrip();
        mainPage.search();

        resultPage.getRandomPlaceType(resultPage.getRandomTrain());
        resultPage.getRandomPlace();
        resultPage.fillPassengerForm();
        resultPage.fillContacts();
        resultPage.acceptOfferta();
        resultPage.submit();

//=========== Choosing a backward ticket =============//

        resultPage.getRandomPlaceType(resultPage.getRandomTrain());
        resultPage.getRandomPlace();
        resultPage.submit();

//=========== Checking of ticket correctness =========//

        assertTrue(purchasePage.confirmTrain(ticket.trainNumber));
        assertTrue(purchasePage.confirmTrain(ticket.trainNumberRound));

        assertTrue(purchasePage.confirmDateAndCity(
                ticket.getForwardDate(),ticket.getForwardCity()));
        assertTrue(purchasePage.confirmDateAndCity(
                ticket.getBackwardDate(),ticket.getBackwardCity()));

        assertTrue(purchasePage.confirmNameAndPlaceType(
                ticket.lastFirstNames,ticket.placeType));
        assertTrue(purchasePage.confirmNameAndPlaceType(
                ticket.lastFirstNames,ticket.placeTypeRound));

        assertTrue(purchasePage.confirmPrice(ticket.price,ticket.priceRound));

//========== Confirm paying =============//

        purchasePage.payBy(PRIVAT_24);
        purchasePage.submit();

        assertThat(getUrl().contains(LIQPAY),
                "Redirect to payment page hasn't been fulfilled");

    }
}
