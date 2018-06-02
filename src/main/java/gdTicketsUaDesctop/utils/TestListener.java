package gdTicketsUaDesctop.utils;

import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.TestListenerAdapter;
import static gdTicketsUaDesctop.utils.CommonServices.*;
import static gdTicketsUaDesctop.utils.loggers.Logger.info;

/**
 * @author Nazar on 17.11.2016.
 */
public class TestListener extends TestListenerAdapter {

    @Override
    public void onTestFailure(ITestResult tr) {
        String name = getTestClassName(tr.getTestClass().getName());
//        takeScreenshot(name, getUrl());
//        AllureLoggerHandler.error("TestCase: \"" + name + "\" FAILED.");
    }



    @Override
    public void onStart(ITestContext testContext) {
        info("=========== * Test suite is started * ===============");
    }

    @Override
    public void onFinish(ITestContext testContext) {
        info("=========== * Test suite is finished * ==============");
    }
}

