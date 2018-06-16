package gdTicketsUaDesctopTest.buyTicketTestSuite;

import backend.xml.NetsuiteGetListRequestBuilder;
import backend.xml.XmlApi;
import backend.xml.XmlRequestTemplateConverter;
import gdTicketsUaDesctop.businessObjects.Passenger;
import gdTicketsUaDesctop.businessObjects.Ticket;
import gdTicketsUaDesctop.pages.MainPage;
import gdTicketsUaDesctop.pages.PurchasePage;
import gdTicketsUaDesctop.pages.resultPage.ResultPage;
import gdTicketsUaDesctop.utils.PropertyReader;
import gdTicketsUaDesctop.utils.TestListener;
import gdTicketsUaDesctopTest.DefaultTestCase;
import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import org.dom4j.Document;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.Map;

import static gdTicketsUaDesctop.Constants.BASE_URL;
import static gdTicketsUaDesctop.Constants.NETSUITE_LOAD_HOST;
import static gdTicketsUaDesctop.Constants.PROPERTY_LOCATION;
import static gdTicketsUaDesctop.utils.CommonServices.goTo;
import static gdTicketsUaDesctop.utils.loggers.AllureLoggerHandler.attachLogsToStep;
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
        backwardTicket = new Ticket();
        purchasePage = new PurchasePage();

        PropertyReader propertyReader = new PropertyReader(PROPERTY_LOCATION + "passenger/adult.properties");
        passenger = new Passenger.PassengerBuilder().
                withFirstName(propertyReader.getValue("firstName")).
                withLastName(propertyReader.getValue("lastName")).
                withEmail(String.valueOf(System.currentTimeMillis()) + "@inbox.ru").
                withPhone(propertyReader.getValue("phone")).
                build();
    }

    @Test
    public void Backend_Test() {
        PropertyReader prop = new PropertyReader(PROPERTY_LOCATION + "netsuite/getListRequest.properties");

        Document xml = XmlRequestTemplateConverter.convertXmlFileToDocument("netsuiteGetList.xml");
        NetsuiteGetListRequestBuilder xmlBuilder = new NetsuiteGetListRequestBuilder(xml);
        String xmlRequest = xmlBuilder.withAccount(prop.getValue("netsuite_load_account")).
                withApplicationId(prop.getValue("netsuite_load_applicationId")).
                withEmail(prop.getValue("netsuite_load_email")).
                withPassword(prop.getValue("netsuite_load_password")).
                withRole(prop.getValue("netsuite_load_role")).
                withExternalId("DIG_947359150").
                withType("invoice").
                buildXmlRequestAsString();

        Map<String, String> getListHeaders = new HashMap<>();
        getListHeaders.put("SOAPAction", "getList");

        XmlApi xmlApi = new XmlApi();
        xmlApi.xmlPost(NETSUITE_LOAD_HOST, getListHeaders, xmlRequest);
        attachLogsToStep();
    }

//    @Test
//    @Description("Buy Round Back Ticket")
//    public void BuyTicket_001_BuyRoundTicket() {
//
//        goTo(BASE_URL);
//
//        MainPage forwardTicketMainPage = new MainPage(forwardTicket);
//        forwardTicket.setDepartureCity("Львов");
//        forwardTicket.setArrivalCity("Киев");
//        forwardTicket.setDepartureDaysInRandomRange(7);
//        forwardTicketMainPage.getTicket();
//
//        MainPage backwardTicketMainPage = new MainPage(backwardTicket);
//        backwardTicket.setDepartureDate(forwardTicket.getDepartureDate().plusDays(3));
//        backwardTicketMainPage.getRoundTrip();
//        backwardTicketMainPage.search();
//
//        ResultPage forwardTicketResultPage = new ResultPage(passenger, forwardTicket);
//        forwardTicketResultPage.getRandomPlaceType(forwardTicketResultPage.getRandomTrain());
//        forwardTicketResultPage.getRandomPlace();
//        forwardTicketResultPage.fillContacts();
//        forwardTicketResultPage.fillPassengerForm();
//        forwardTicketResultPage.checkOneTea();
//        forwardTicketResultPage.checkCargo();
//        forwardTicketResultPage.submit();
//
////=========== Choosing a backward ticket =============//
//        ResultPage backwardTicketResultPage = new ResultPage(passenger, backwardTicket);
//        backwardTicketResultPage.getRandomPlaceType(backwardTicketResultPage.getRandomTrain());
//        backwardTicketResultPage.getRandomPlace();
//        backwardTicketResultPage.checkTwoTea();
//        backwardTicketResultPage.checkNoBed();
//        backwardTicketResultPage.submit();

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

//    }
}
