package gdTicketsUaDesctop.utils.testCrestWest;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.time.LocalDate;
import java.time.LocalDateTime;

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
        System.out.println(LocalDateTime.now());
    }


}
