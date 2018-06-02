package gdTicketsUaDesctop.utils.testCrestWest;

import org.openqa.selenium.By;

import java.io.*;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

class Son extends Dad
{

    public String transType = "oo";
    public String status = "lopi";
    public String catalogItemType;
    public String catalogType;
    private static String statuari = "SANCTUS";

    static File file;
    static FileInputStream fis;
    static FileOutputStream fos;




    protected  String me = "son";


    public void popka()
    {
        System.out.println("DAAAAAAD");
    }

    public  Telecom roberto(){
        return Dad.tutu;
    }

    public Son(String o){
        super(33);
//        statuari = rufus;
    }

    static void clean(){
        try {
            fos = new FileOutputStream(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }


    @Override
    public String toString() {
        return "transType: " + transType + "\n" +
                "status: " + status + "\n" +
                "catalogItemType: " + catalogItemType + "\n" +
                "catalogType: " + catalogType;
    }

    @Override
    public int hashCode() {
        return 1254875658;
    }

    public static void main(String[] args) {
        By from = By.xpath("//*[@id='from_name_as']");
        String po = from.toString().replaceAll("By\\.\\w{1,10}:\\s", "");

        System.out.println("'"+po+"'");
    }
}
