package gdTicketsUaDesctop.utils.testCrestWest;

import gdTicketsUaDesctop.businessObjects.Passenger;
import gdTicketsUaDesctop.utils.PropertyReader;

import static gdTicketsUaDesctop.Constants.PROPERTY_LOCATION;

public class TestRest extends Telecom {

    private String rr = "Romario";
    private String hoh = "bandit";
    public String koko = super.koko;
    private int age = 130;
    private static int lo = 1000;
    private Integer popo = 333;


    public void hytra(Inter rom) {
        String lo = rom.kilo(12);
        System.out.println(lo.toUpperCase());

    }




    public static void main(String[] args) {
        PropertyReader propertyReader = new PropertyReader(
                PROPERTY_LOCATION + "passenger/" + "adult.properties");
        Passenger pass = new Passenger.PassengerBuilder().
                withFirstName(propertyReader.getValue("firstName")).
                withLastName(propertyReader.getValue("lastName")).
                withEmail(propertyReader.getValue("email")).
                withPhone(propertyReader.getValue("phone")).
                build();

//        TestRest tr = new TestRest();
//        tr.hytra(popo -> popo.floatValue() + " hui");


    }
}
