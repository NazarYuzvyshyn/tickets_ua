package gdTicketsUaDesctop.businessObjects;

import gdTicketsUaDesctop.utils.PropertyReader;
import static gdTicketsUaDesctop.Constants.PROPERTY_LOCATION;

/**
 * @author Nazar on 03.11.2016.
 */
public class Passenger {

    private String creditCardDigits;
    private String firstName;
    private String lastName;
    private String paySystem;
    private String age;
    private String monthOfCard;
    private String yearOfCard;
    private String email;
    private String phone;
    private String phoneCode;
    private String cvv;
    private String tea;
    private String noBed;
    private String cargo;
    private String reserve;

    public Passenger(String fileLocation) {
        PropertyReader propertyReader = new PropertyReader(PROPERTY_LOCATION + "passenger/" + fileLocation);
        this.creditCardDigits = propertyReader.getValue("creditCard");
        this.monthOfCard = propertyReader.getValue("monthOfCard");
        this.yearOfCard = propertyReader.getValue("yearOfCard");
        this.paySystem = propertyReader.getValue("paySystem");
        this.age = propertyReader.getValue("age");
        this.firstName = propertyReader.getValue("firstName");
        this.lastName = propertyReader.getValue("lastName");
        this.email = propertyReader.getValue("email");
        if (email.startsWith("@")) email = String.valueOf(System.currentTimeMillis()) + email;
        this.phone = propertyReader.getValue("phone");
        this.phoneCode = propertyReader.getValue("phoneCode");
        this.cvv = propertyReader.getValue("cvv");
        this.tea = propertyReader.getValue("tea");
        this.noBed = propertyReader.getValue("noBed");
        this.cargo = propertyReader.getValue("cargo");
        this.reserve = propertyReader.getValue("reserve");
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmail() {
        return email;
    }

    public String getPhone() {
        return phone;
    }

    public String getTea() {
        return tea;
    }

    public String getNoBed() {
        return noBed;
    }

    public String getCargo() {
        return cargo;
    }
}


