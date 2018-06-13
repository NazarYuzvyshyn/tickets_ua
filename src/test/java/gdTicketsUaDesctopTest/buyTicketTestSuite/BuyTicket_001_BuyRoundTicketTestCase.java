package gdTicketsUaDesctopTest.buyTicketTestSuite;

import gdTicketsUaDesctop.businessObjects.Passenger;
import gdTicketsUaDesctop.businessObjects.Ticket;
import gdTicketsUaDesctop.pages.MainPage;
import gdTicketsUaDesctop.pages.PurchasePage;
import gdTicketsUaDesctop.pages.resultPage.ExtraServices;
import gdTicketsUaDesctop.pages.resultPage.ResultPage;
import gdTicketsUaDesctop.utils.PropertyReader;
import gdTicketsUaDesctop.utils.TestListener;
import gdTicketsUaDesctopTest.DefaultTestCase;
import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import static gdTicketsUaDesctop.Constants.BASE_URL;
import static gdTicketsUaDesctop.Constants.PROPERTY_LOCATION;
import static gdTicketsUaDesctop.utils.CommonServices.goTo;
import static org.testng.Assert.assertTrue;


@Listeners({TestListener.class})
@Feature("Buy Tickets Feature")
public class BuyTicket_001_BuyRoundTicketTestCase extends DefaultTestCase {

    private Ticket forwardTicket;
    private Ticket backwardTicket;
    private Passenger passenger;
    private PurchasePage purchasePage;

    @BeforeTest
    public void before() {
        forwardTicket = new Ticket();
        forwardTicket.extraServices = new ExtraServices();

        forwardTicket.setDepartureCity("Львов");
        forwardTicket.setArrivalCity("Киев");
        forwardTicket.setDepartureDaysInRandomRange(7);
//        forwardTicket.extraServices.setCargo(true);
//        forwardTicket.extraServices.setOneTea(true);


        backwardTicket = new Ticket();
        backwardTicket.extraServices = new ExtraServices();

//        backwardTicket.setDepartureCity("Киев");
//        backwardTicket.setArrivalCity("Львов");
        backwardTicket.setDepartureDate(forwardTicket.getDepartureDate().plusDays(3));
//        backwardTicket.extraServices.setTwoTea(true);
//        backwardTicket.extraServices.setNoBed(true);

        purchasePage = new PurchasePage();

        PropertyReader propertyReader = new PropertyReader(PROPERTY_LOCATION + "passenger/adult.properties");
        passenger = new Passenger.PassengerBuilder().
                withFirstName(propertyReader.getValue("firstName")).
                withLastName(propertyReader.getValue("lastName")).
                withEmail(propertyReader.getValue("email")).
                withPhone(propertyReader.getValue("phone")).
                build();
    }


    @Test
    @Description("Buy Round Back Ticket")
    public void BuyTicket_001_BuyRoundTicket() {

        goTo(BASE_URL);

        MainPage forwardTicketMainPage = new MainPage(forwardTicket);
        forwardTicketMainPage.getTicket();

        MainPage backwardTicketMainPage = new MainPage(backwardTicket);
        backwardTicketMainPage.getRoundTrip();

        backwardTicketMainPage.search();

        ResultPage forwardTicketResultPage = new ResultPage(passenger, forwardTicket);
        forwardTicketResultPage.getRandomPlaceType(forwardTicketResultPage.getRandomTrain());
        forwardTicketResultPage.getRandomPlace();
        forwardTicketResultPage.fillContacts();
        forwardTicketResultPage.fillPassengerForm();

        forwardTicketResultPage.checkOneTea();
        forwardTicketResultPage.checkCargo();

        forwardTicketResultPage.acceptOfferta();
        forwardTicketResultPage.submit();

//=========== Choosing a backward ticket =============//
        ResultPage backwardTicketResultPage = new ResultPage(passenger, backwardTicket);
        backwardTicketResultPage.getRandomPlaceType(backwardTicketResultPage.getRandomTrain());
        backwardTicketResultPage.getRandomPlace();

        backwardTicketResultPage.checkTwoTea();
        backwardTicketResultPage.checkNoBed();

        backwardTicketResultPage.submit();

////=========== Checking of forwardTicket correctness =========//
//
//        assertTrue(purchasePage.confirmTrain(forwardTicket.trainNumberAndName));
//        assertTrue(purchasePage.confirmTrain(forwardTicket.trainNumberRound));
//
//        assertTrue(purchasePage.confirmDateAndCity(
//                forwardTicket.getForwardDate(),forwardTicket.getForwardCity()));
//        assertTrue(purchasePage.confirmDateAndCity(
//                forwardTicket.getBackwardDate(),forwardTicket.getArrivalCity()));
//
//        assertTrue(purchasePage.confirmNameAndPlaceType(
//                forwardTicket.lastFirstNames,forwardTicket.placeType));
//        assertTrue(purchasePage.confirmNameAndPlaceType(
//                forwardTicket.lastFirstNames,forwardTicket.placeTypeRound));
//
//        assertTrue(purchasePage.confirmPrice(forwardTicket.price,forwardTicket.priceRound));
//
////========== Confirm paying =============//
//
//        purchasePage.payBy(PRIVAT_24);
//        purchasePage.submit();
//
//        assertThat(getUrl().contains(LIQPAY),
//                "Redirect to payment page hasn't been fulfilled");

    }
}
