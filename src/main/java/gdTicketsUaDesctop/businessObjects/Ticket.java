package gdTicketsUaDesctop.businessObjects;

import gdTicketsUaDesctop.utils.PropertyReader;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;

import static gdTicketsUaDesctop.Constants.PROPERTY_LOCATION;
import static java.lang.Integer.parseInt;

/**
 * @author Nazar on 03.11.2016.
 */
public class Ticket {
//============ Desired Ticket =============//
    private String forwardDate;
    private String forwardCity;
    private int forwardDateRange;
    private String backwardDate;
    private String backwardCity;
    private int backwardAfterDays;
    private String round;
    private static Calendar calendar = Calendar.getInstance();
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
        this.forwardDateRange = parseInt(propertyReader.getValue("departureDateRange"));
        Date date = subtractMonth(new Date());
        Date forwardDate = addDays(date, forwardDateRange, exactOrRandomDate);
        this.forwardDate = formatDate(forwardDate, "dd.MM.yyyy");
        this.backwardAfterDays = parseInt(propertyReader.getValue("backAfterDays"));
        Date backwardDate = addDays(forwardDate, backwardAfterDays, exactOrRandomDate);
        this.backwardDate = formatDate(backwardDate, "dd.MM.yyyy");
        this.forwardCity = propertyReader.getValue("departureFrom");
        this.backwardCity = propertyReader.getValue("arrivalTo");
        this.round = propertyReader.getValue("round");
    }

    /**
     * Add count of days to given date if 'exactOrRandom' = 'exact' and return new date or,
     * if 'exactOrRandom' = 'random' add count of days to given date and return random date in this range.
     * @param date          = desired date
     * @param days          = exactly days for adding or range
     * @param exactOrRandom = "random" or "exact"
     * @return new date
     */
    private static Date addDays(Date date, int days, String exactOrRandom) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        if (exactOrRandom.equals("random")) {
            Random ran = new Random();
            days = ran.nextInt(days);
        }
        calendar.add(Calendar.DATE, days);
        return calendar.getTime();
    }

    /** Subtract one month from current date,because first month
     * on gd.tickets.us was began from '0'.
     * @param date current date
     * @return current data decreased on month
     */
    private static Date subtractMonth(Date date){
        calendar.setTime(date);
        calendar.add(Calendar.MONTH, -1);
        return calendar.getTime();
    }

    private static String formatDate(Date date, String format) {
        DateFormat dateFormat = new SimpleDateFormat(format);
        return dateFormat.format(date);
    }

    public String getForwardCity() {
        return forwardCity;
    }

    public String getBackwardCity() {
        return backwardCity;
    }

    public String getForwardDate() {
        return forwardDate;
    }

    public String getBackwardDate() {
        return backwardDate;
    }

}
