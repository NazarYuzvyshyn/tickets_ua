package gdTicketsUaDesctop.utils.testCrestWest;

public class UpperTelecom implements Inter{
    public String oo = "LOLIPIO";
    public Integer po = 255;

    public void mono(Inter i){

        System.out.println("UpperTelecom mono()");
    }

    public static void main(String[] args) {
        Inter intr = new UpperTelecom();
        UpperTelecom up = (UpperTelecom)intr;
//        System.out.println(intr == up);

    }

    @Override
    public String kilo(Integer popo) {
        System.out.println("kilo");
        return "";
    }

    @Override
    public <T> String kilo_2(T popo) {
        return null;
    }

}

