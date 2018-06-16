package gdTicketsUaDesctop.businessObjects;

import gdTicketsUaDesctop.pages.resultPage.ExtraServices;
import gdTicketsUaDesctop.utils.PropertyReader;
import java.time.LocalDate;
import java.util.Random;
import static gdTicketsUaDesctop.Constants.PROPERTY_LOCATION;
import static gdTicketsUaDesctop.utils.loggers.Logger.info;
import static java.lang.Integer.parseInt;

/**
 * @author Nazar on 03.11.2016.
 */
public class Ticket {
//============ Desired Ticket =============//
    private LocalDate departureDate;
    private String departureCity;
    private LocalDate arrivalDate;
    private String arrivalCity;
//============ Chosen Ticket ==============//
    private String trainNumberAndName;
//    public String trainNumberRound;
    private String placeType;
//    public String placeTypeRound;
    private String placeNumber;
//    public String placeNumberRound;
    private String lastFirstNames;
    private String price;
//    public String priceRound;
    public ExtraServices extraServices;

    /**
     * Make ticket object from property file
     * @param fileLocation      = short file location of ticket folder property
     * @param exactOrRandomDate = "exact" - for exact departureDate of departure.Gives this departureDate after add count
     *                          of days,which was defined in property,to current departureDate.
     *                          "random" - add count of days,which was defined in property,to current departureDate
     *                          and gets random departureDate in this range.
     */
//    public Ticket(String fileLocation, String exactOrRandomDate) {
//        PropertyReader propertyReader = new PropertyReader(PROPERTY_LOCATION + "ticket/" + fileLocation);
//        int daysFromNow = parseInt(propertyReader.getValue("departureDaysFromNow"));
//        this.departureDate = addDays(LocalDate.now(), daysFromNow, exactOrRandomDate);
//
//        this.departureCity = propertyReader.getValue("departureFrom");
//        this.arrivalCity = propertyReader.getValue("arrivalTo");
//    }

    /**
     * Add count of days to given departureDate if 'exactOrRandom' = 'exact' and return new departureDate or,
     * if 'exactOrRandom' = 'random' add count of days to given departureDate and return random departureDate in this range.
     * @param date          = desired departureDate
     * @param days          = exactly days for adding or range
     * @param exactOrRandom = "random" or "exact"
     * @return new departureDate
     */
    private LocalDate addDays(LocalDate date, int days, String exactOrRandom) {
        if (exactOrRandom.equals("random")) {
            Random ran = new Random();
            days = ran.nextInt(days);
        }
        date = date.plusDays(days);
        info(String.format("Was added %s days from now, new departure date: %s", days, date));
        return date;
    }

    public String getDepartureCity() {
        return departureCity;
    }

    public void setDepartureCity(String departureCity) {
        info(String.format("Departure city: %s", departureCity));
        this.departureCity = departureCity;
    }

    public LocalDate getDepartureDate() {
        return departureDate;
    }

    public void setDepartureDate(LocalDate departureDate) {
        info(String.format("Departure date: %s", departureDate));
        this.departureDate = departureDate;
    }

    public void setDepartureDaysFromNow(int days){
        departureDate = addDays(LocalDate.now(), days, "exact");
    }

    public void setDepartureDaysInRandomRange(int days){
        departureDate = addDays(LocalDate.now(), days, "random");
    }

    public LocalDate getArrivalDate() {
        return arrivalDate;
    }

    public void setArrivalDate(LocalDate arrivalDate) {
        this.arrivalDate = arrivalDate;
    }

    public String getArrivalCity() {
        return arrivalCity;
    }

    public void setArrivalCity(String arrivalCity) {
        this.arrivalCity = arrivalCity;
    }

    public String getTrainNumberAndName() {
        return trainNumberAndName;
    }

    public void setTrainNumberAndName(String trainNumberAndName) {
        this.trainNumberAndName = trainNumberAndName;
    }

    public String getPlaceType() {
        return placeType;
    }

    public void setPlaceType(String placeType) {
        this.placeType = placeType;
    }

    public String getPlaceNumber() {
        return placeNumber;
    }

    public void setPlaceNumber(String placeNumber) {
        this.placeNumber = placeNumber;
    }

    public String getLastFirstNames() {
        return lastFirstNames;
    }

    public void setLastFirstNames(String lastFirstNames) {
        this.lastFirstNames = lastFirstNames;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }
}
