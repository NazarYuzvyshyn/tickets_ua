package gdTicketsUaDesctop.utils.testCrestWest;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class Telecom implements Inter, Inurik {

    public String koko = "KOKO";
    public static String stat = "STATIC";

    @Override
    public String kilo(Integer popo) {
        System.out.println("kilo() Telecom");
        return "";
    }

    @Override
    public <T> String kilo_2(T popo) {
        return "ficus";
    }

    @Override
    public void pobutyDruga(String imiaDruga) {

    }


    public static void main(String[] args) {
        Inter i = new Telecom();
        List<String> list1 = new ArrayList<>();
        list1.add("a");
        list1.add("a");
        list1.add("b");
        List<String> list2 = new ArrayList<>();
        list2.add("b");
        list2.add("b");
        list2.add("a");

        i.kilo_2("s");

        HashSet<String> set1 = new HashSet<>(list1);
        HashSet<String> set2 = new HashSet<>(list2);
        System.out.println(set1);
        System.out.println(set2);
        boolean g = set1.equals(set2);
        System.out.println(g);
    }


}
