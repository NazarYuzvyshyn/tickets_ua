package gdTicketsUaDesctop.utils.testCrestWest;

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
        int op;
       if (2 == 2){
            op = 23569;
       }

        System.out.println(op);
    }
}
