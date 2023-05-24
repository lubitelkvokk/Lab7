package client.message_processing.sending;

import java.net.*;

public class ServerAvailabilityChecker {
    public static boolean checkServer(int port) {
        String host = "localhost"; // или "127.0.0.1"
        try (DatagramSocket socket = new DatagramSocket(port)) {
            socket.setReuseAddress(true);
            return true;
        } catch (SocketException e) {
            System.out.println("Ошибка создания сокета");
            e.printStackTrace();
            return false;
        } catch (Exception e) {
            System.out.println("Сервер недоступен");
            e.printStackTrace();
            return false;
        }
    }
}