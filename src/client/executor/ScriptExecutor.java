package client.executor;

import client.ClientReceiver;
import client.ResponseValidator;
import client.readers.IReader;
import client.readers.Reader;
import client.readers.ReaderS;
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
import static client.factory.port.PortFactory.getPort;
import static client.message_processing.getting.MessageGetter.getMessage;
import static client.message_processing.sending.MessageSender.sendMessage;

public class ScriptExecutor {

    public void runInteractiveScriptMode(String path, IReader reader, DatagramChannel client, InetSocketAddress address) throws IOException {
        reader = new Reader(new Scanner(reader.readFromFile(path)));
        ClientReceiver clientReceiver = new ClientReceiver();
        CommandReader commandReader = new CommandReader(reader);
        while (true) {
            try {
                Message message = commandReader.getMessage();
                System.out.println(message.getCommand());
                if (message.getCommand().equals(CommandsEnum.EXECUTE_SCRIPT)) {
                    if (message.getCommand().equals(CommandsEnum.EXECUTE_SCRIPT)) {
                        ScriptExecutor scriptExecutor = new ScriptExecutor();
                        scriptExecutor.runInteractiveScriptMode(message.getData(), reader, client, address);
                        reader = new ReaderS(new Scanner(System.in));
                    }
                }
                if (message.getCommand() != null) {
                    // Отправка message
                    sendMessage(client, message, address);
                    // Получение msg
                    Message response = getMessage(client);
                    ResponseValidator.responseValidate(response);
                }
            } catch (PortUnreachableException e) {
                System.out.printf(ColorLogger.logErr("Сервер временно недоступен") + '\n');
            } catch (NullPointerException | NoSuchElementException e) {
                System.out.printf(ColorLogger.logErr("Прекращение ввода") + '\n');
                System.exit(1);
            } catch (Exception e) {
                System.out.printf(ColorLogger.logErr(e.getMessage()) + '\n');
            }
        }

    }
}