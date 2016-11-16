package gdTicketsUaDesctop.utils;

import org.apache.log4j.Logger;
import ru.yandex.qatools.allure.annotations.Attachment;

/**
 * @author  Назар on 14.09.2016.
 */
public class Log {

    private static Logger Log = Logger.getLogger("");

    @Attachment(type = "text/plain")
    public static <T extends Object> void info(T message) {
        Log.info(String.valueOf(message));
    }
    public static <T extends Object> void error(T message) {
        Log.error(String.valueOf(message));
    }
    @Attachment(type = "text/plain")
    public static <T extends Object> void warn(T message) {
        Log.warn(String.valueOf(message));
    }

}