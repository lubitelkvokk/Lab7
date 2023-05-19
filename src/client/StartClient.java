package client;

import client.executor.CommandReader;
import client.message_processing.SendAndGet;
import client.readers.IReader;
import client.readers.ReaderS;
import client.script.ScriptManager;
import mid.commands.CommandsEnum;
import mid.fabrics.message.instance.Message;
import mid.logger.ColorLogger;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.PortUnreachableException;
import java.nio.channels.DatagramChannel;
import java.util.NoSuchElementException;
import java.util.Scanner;

import static client.factory.channel.ChannelFactory.getChannel;


public class StartClient {


    public static void main(String[] args) throws IOException {
        IReader reader = new ReaderS(new Scanner(System.in));
        CommandReader commandReader = new CommandReader(reader);
        InetSocketAddress address = new InetSocketAddress("127.0.0.1", Integer.parseInt(args[0].trim()));
        DatagramChannel client = getChannel();
        client.connect(address);
        while (true) {
            try {
                Message message = commandReader.getMessage();
                if (message.getCommand().equals(CommandsEnum.EXECUTE_SCRIPT)) {

                    ScriptManager scriptManager = new ScriptManager(reader);
                    //получаем список сообщений, который необходимо выполнить
                    scriptManager.getMessagesFromFile(message.getData());
                    for (Message x : scriptManager.getHistory()) {
                        // отправляем на сервер по сообщению, чтобы не задерживать очередь и обрабатывает ответ
                        ResponseValidator.responseValidate(SendAndGet.sendMessageGetResponse(client, address, x));
                    }
                    // очищаем историю сообщений, т.к. история static и не обновляется для нового scriptManager
                    scriptManager.clearHistory();
                } else {
                    if (message.getCommand() != null) {
                        ResponseValidator.responseValidate(SendAndGet.sendMessageGetResponse(client, address, message));
                    }
                }

            } catch (PortUnreachableException e) {
                System.out.printf(ColorLogger.logErr("Сервер временно недоступен") + '\n');
            } catch (NoSuchElementException e) {
                System.out.printf(ColorLogger.logErr("Прекращение ввода") + '\n');
                System.exit(1);
            } catch (NullPointerException e) {
                e.printStackTrace();
                System.out.printf(ColorLogger.logErr("Прекращение ввода") + '\n');
                System.exit(1);
            } catch (Exception e) {
                System.out.printf(ColorLogger.logErr(e.getMessage()) + '\n');
            }
        }
    }

}
