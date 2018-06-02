package gdTicketsUaDesctop.utils.loggers;

import java.time.LocalDateTime;

import static gdTicketsUaDesctop.Constants.LOG_FILE_DIR;

public class Logger {

    private static org.apache.log4j.Logger Log;

    public static String initLogFile(){
        String threadSafeLogFile = LOG_FILE_DIR + LocalDateTime.now().toString();
        System.setProperty("LOG_FILE", threadSafeLogFile);
        Log = org.apache.log4j.Logger.getLogger("");
        info("LOG_FILE: " + threadSafeLogFile);
        return threadSafeLogFile;
    }

//    String getLogFilePath(){
//        return threadSafeLogFile;
//    }

//    public abstract void saveLogs();

    public static void info(String message) {
        Log.info(message);
    }

    public static void error(String message) {
        Log.error(message);
    }

    public static void debug(String message) {
        Log.warn(message);
    }
}
