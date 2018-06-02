package gdTicketsUaDesctop.businessObjects;

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
    private LocalDate forwardDate;
    private String forwardCity;
    private LocalDate backwardDate;
    private String backwardCity;
//============ Chosen Ticket ==============//
    public String trainNumber;
    public String trainNumberRound;
    public String placeType;
    public String placeTypeRound;
    public String placeNumber;
    public String placeNumberRound;
    public String lastFirstNames;
    public String price;
    public String priceRound;

    /**
     * Make ticket object from property file
     * @param fileLocation      = short file location of ticket folder property
     * @param exactOrRandomDate = "exact" - for exact date of departure.Gives this date after add count
     *                          of days,which was defined in property,to current date.
     *                          "random" - add count of days,which was defined in property,to current date
     *                          and gets random date in this range.
     */
    public Ticket(String fileLocation, String exactOrRandomDate) {
        PropertyReader propertyReader = new PropertyReader(PROPERTY_LOCATION + "ticket/" + fileLocation);
        int forwardDateRange = parseInt(propertyReader.getValue("departureDateRange"));
        this.forwardDate = addDays(LocalDate.now(), forwardDateRange, exactOrRandomDate);

        int backwardAfterDays = parseInt(propertyReader.getValue("backAfterDays"));
        this.backwardDate = addDays(forwardDate, backwardAfterDays, exactOrRandomDate);

        this.forwardCity = propertyReader.getValue("departureFrom");
        this.backwardCity = propertyReader.getValue("arrivalTo");
    }

    /**
     * Add count of days to given date if 'exactOrRandom' = 'exact' and return new date or,
     * if 'exactOrRandom' = 'random' add count of days to given date and return random date in this range.
     * @param date          = desired date
     * @param days          = exactly days for adding or range
     * @param exactOrRandom = "random" or "exact"
     * @return new date
     */
    private static LocalDate addDays(LocalDate date, int days, String exactOrRandom) {
        if (exactOrRandom.equals("random")) {
            Random ran = new Random();
            days = ran.nextInt(days);
        }
        date = date.plusDays(days);
        info(String.format("Was added %s days from now, new date: %s", days, date));
        return date;
    }

    public String getForwardCity() {
        return forwardCity;
    }

    public String getBackwardCity() {
        return backwardCity;
    }

    public LocalDate getForwardDate() {
        info(String.format("Forward date: %s", forwardDate));
        return forwardDate;
    }

    public LocalDate getBackwardDate() {
        info(String.format("Backward date: %s", backwardDate));
        return backwardDate;
    }

}
