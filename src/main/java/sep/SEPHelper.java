package sep;

public class SEPHelper {
    public static String ticksToString(int ticks){
        System.out.println(ticks);
        int totalS = ticks / 20;
        int h = totalS / 3600;
        int m = (totalS % 3600) / 60;
        int s = totalS % 60;
        return h > 0 ?
                h + ":" + format(m) + ":" + format(s)
                :                   m + ":" + format(s);
    }

    private static String format(int n) {
        return n == 0 ?
                "00"
                : (n / 10 == 0) ?
                    "0" + n
                    : String.valueOf(n);
    }
}
