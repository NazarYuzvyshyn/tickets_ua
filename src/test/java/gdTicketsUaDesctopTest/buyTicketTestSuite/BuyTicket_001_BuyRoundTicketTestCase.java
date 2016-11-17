package gdTicketsUaDesctopTest.buyTicketTestSuite;

import gdTicketsUaDesctop.businessObjects.Passenger;
import gdTicketsUaDesctop.businessObjects.Ticket;
import gdTicketsUaDesctop.pages.MainPage;
import gdTicketsUaDesctop.pages.PurchasePage;
import gdTicketsUaDesctop.pages.ResultPage;
import gdTicketsUaDesctopTest.DefaultTestCase;
import org.testng.annotations.Test;
import ru.yandex.qatools.allure.annotations.Title;

import static gdTicketsUaDesctop.Constants.BASE_URL;
import static gdTicketsUaDesctop.Constants.LIQPAY;
import static gdTicketsUaDesctop.pages.PurchasePage.PayBy.PRIVAT_24;
import static gdTicketsUaDesctop.utils.CommonServices.assertThat;
import static gdTicketsUaDesctop.utils.CommonServices.getUrl;
import static gdTicketsUaDesctop.utils.CommonServices.goTo;
import static gdTicketsUaDesctop.utils.WebDriverFactory.getDriver;
import static org.testng.Assert.assertTrue;

/**
 * @author Nazar on 04.11.2016.
 */
public class BuyTicket_001_BuyRoundTicketTestCase extends DefaultTestCase {

    private Ticket ticket = new Ticket("buyRound.properties", "random");
    private MainPage mainPage = new MainPage(ticket, getDriver());
    private ResultPage resultPage = new ResultPage(new Passenger("adult.properties"),ticket, getDriver());
    private PurchasePage purchasePage = new PurchasePage(getDriver());

    @Test
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
