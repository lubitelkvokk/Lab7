package client.factory.port;

import mid.logger.ColorLogger;

public class PortFactory {


    public static int getPort(String str) {
        if (str.trim().equals("")) {
            return 10243;
        }
        try {
            int port = Integer.parseInt(str.trim());
            return port;
        } catch (Exception e) {
            System.out.printf(ColorLogger.logErr("Данный порт не подходит") + '\n');
        }
        return 8080;
    }

}
