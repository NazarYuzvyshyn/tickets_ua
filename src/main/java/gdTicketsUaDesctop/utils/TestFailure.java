package gdTicketsUaDesctop.utils;

import gdTicketsUaDesctop.utils.loggers.AllureLoggerHandler;
import org.testng.Assert;

import static gdTicketsUaDesctop.utils.loggers.Logger.error;
import static gdTicketsUaDesctop.utils.loggers.Logger.info;

public class TestFailure {

    public static void failTest(String message, String detailedMessage){
        error(message);
        info(">>>>>>>>>>-------   Detailed Message of Exception   --------<<<<<<<<<<<");
        error(detailedMessage);
        Assert.fail();
    }

    public static void failTest(String message){
        error(message);
        Assert.fail();
    }
}
