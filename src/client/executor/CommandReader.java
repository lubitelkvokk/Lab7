package client.executor;


import client.ClientReceiver;
import client.exeptions.InputException;
import client.readers.IReader;
import mid.commands.CommandsEnum;
import mid.fabrics.message.instance.COMMAND_TYPE;
import mid.fabrics.message.instance.Message;
import mid.fabrics.message.instance.MessageType;
import mid.logger.ColorLogger;

import java.io.IOException;
import java.util.NoSuchElementException;

//TODO Можно назвать класс как Terminal

/**
 * Суть этого класса заключается в том, что он выполняет роль терминала.
 * Грубо говоря ему передается устройство(пользователь), в котором прописано выполнение команд.
 * Передавая ему команды, мы абстрагируемся от того, как ему выполнить эту команду и есть ли она в его списке.
 */
public class CommandReader {

    ClientReceiver clientReceiver = new ClientReceiver();
    public IReader reader;

    public CommandReader(IReader reader) {
        this.clientReceiver = clientReceiver;
        this.reader = reader;
    }

    public void setReader(IReader reader) {
        this.reader = reader;
    }

    public Message getMessage() throws IOException {
        String command = "";
        Message message = new Message();
            command = reader.readCommand();

        if (command.trim().equals("exit")) {
            System.exit(1);
        }
        CommandsEnum cmd = clientReceiver.searchCommand(command);
        if (cmd == null) {
            throw new InputException("Команда введена неверно");
        }
        message.setCommand(cmd);
        if (MessageType.getType(cmd) == COMMAND_TYPE.INT) {
            message.setId(Integer.valueOf(reader.readLine().trim()));
        } else if (MessageType.getType(cmd) == COMMAND_TYPE.STRING) {
            message.setData(reader.readLine().trim());
        } else if (MessageType.getType(cmd) == COMMAND_TYPE.STUDY_GROUP) {
            boolean hasId = false;
            if (cmd.getName().equals("update")) {
                hasId = true;
            }
            message.setStudyGroup(reader.readElement(hasId));
        }

        return message;
    }
}



