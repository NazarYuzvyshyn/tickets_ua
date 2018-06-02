package gdTicketsUaDesctop.utils;

import org.apache.log4j.Logger;
import io.qameta.allure.Step;

/**
 * @author  Назар on 14.09.2016.
 */
public class Log {

    private static Logger Log = Logger.getLogger("");

    @Step("{0}")
    public static void info(String message) {
        Log.info(message);
    }

    @Step("{0}")
    public static void error(String message) {
        Log.error(message);
    }

    @Step("{0}")
    public static void warn(String message) {
        Log.warn(message);
    }

}