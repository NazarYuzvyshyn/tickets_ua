package gdTicketsUaDesctop.pages.resultPage;

import gdTicketsUaDesctop.utils.PropertyReader;
import org.openqa.selenium.By;

import static gdTicketsUaDesctop.Constants.PROPERTY_LOCATION;
import static gdTicketsUaDesctop.utils.WaitFor.WaitCondition.VISIBLE;
import static gdTicketsUaDesctop.utils.WaitFor.waitConditionAndReturnStatus;
import static gdTicketsUaDesctop.utils.WebElementServices.clickOn;
import static gdTicketsUaDesctop.utils.loggers.Logger.info;

public class ExtraServices {

//    private By drink = By.xpath("//*[@id='drink']");
    private boolean oneTea;
    private boolean twoTea;
    private boolean cargo;
    private boolean noBed;

//    private PropertyReader propertyReader;

    public ExtraServices() {
//        propertyReader = new PropertyReader(PROPERTY_LOCATION + "/ticket/" + propertyFileLocation);
    }

    public boolean isOneTea() {
        return oneTea;
    }

    public void setOneTea(boolean oneTea) {
        this.oneTea = oneTea;
    }

    public boolean isTwoTea() {
        return twoTea;
    }

    public void setTwoTea(boolean twoTea) {
        this.twoTea = twoTea;
    }

    public boolean isCargo() {
        return cargo;
    }

    public void setCargo(boolean cargo) {
        this.cargo = cargo;
    }

    public boolean isNoBed() {
        return noBed;
    }

    public void setNoBed(boolean noBed) {
        this.noBed = noBed;
    }

    //    public void checkTeaIfRequestedAndAvailable() {
//        String amountOfTea = propertyReader.getValue("tea");
//        if (waitConditionAndReturnStatus(drink, VISIBLE, 2)) {
//            clickOn("Напиток", drink);
//        } else {
//            info("There is no option 'Напиток' for this train ticket");
//            return;
//        }
//        if (amountOfTea.equals("1") && waitConditionAndReturnStatus(oneTea, VISIBLE, 2))
//            clickOn("1 чай", oneTea);
//        if (amountOfTea.equals("2") && waitConditionAndReturnStatus(twoTea, VISIBLE, 2))
//            clickOn("2 чая", twoTea);
//    }
//
//    public void checkNoBedIfRequestedAndAvailable() {
//        String noBedValue = propertyReader.getValue("noBed");
//        if (Boolean.valueOf(noBedValue) && waitConditionAndReturnStatus(noBed, VISIBLE, 2)) {
//            clickOn("Без постельного белья", noBed);
//        }
//    }
//
//    public void checkCargoIfRequestedAndAvailable() {
//        String cargoValue = propertyReader.getValue("cargo");
//        if (Boolean.valueOf(cargoValue) && waitConditionAndReturnStatus(cargo, VISIBLE, 2)) {
//            clickOn("Дополнительный багаж ", cargo);
//        }
//    }
}
