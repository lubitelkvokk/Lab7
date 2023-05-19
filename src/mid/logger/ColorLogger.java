package mid.logger;

public class ColorLogger {


    public static String logErr(String msg) {
        return "\u001B[31m" + msg + "\u001B[0m";
    }

    public static String logInfo(String msg) {
        return "\u001B[32m" + msg + "\u001B[0m";
    }
}
