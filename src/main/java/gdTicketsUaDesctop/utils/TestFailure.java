package gdTicketsUaDesctop.utils;

import org.testng.Assert;

public class TestFailure {

    public static void failTest(String message, String detailedMessage){
        Log.error(message);
        Log.info(">>>>>>>>>>-------   Detailed Message of Exception   --------<<<<<<<<<<<");
        Log.error(detailedMessage);
        Assert.fail();
    }

    public static void failTest(String message){
        Log.error(message);
        Assert.fail();
    }
}
