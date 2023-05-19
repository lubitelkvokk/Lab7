package server.handler.client_processing;

import mid.commands.CommandsEnum;
import server.collection.manager.CollectionManager;
import mid.adapters.CommandToMessageAdapter;
import mid.fabrics.commands.instance.Command;
import mid.fabrics.message.instance.Message;
import server.exceptions.InputException;

import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CommandExecutor {
    private final Command[] commands;

    private static final String className = CommandExecutor.class.getName();
    private static final Logger logger = Logger.getLogger(className);


    public CommandExecutor(Command[] commands) {
        this.commands = commands;
    }

    public Message runCommand(Command command, CollectionManager collectionManager) {
        logger.entering(className, "runCommand", new Object[]{command, collectionManager});

        if (Arrays.stream(commands).noneMatch(x -> x.getName().equals(command.getName()))) {
            return new Message(CommandsEnum.RESPONSE_ERR, "Данная команда не поддерживается сервером");
        }
        command.setCollectionManager(collectionManager);
        try {
            command.execute();
        } catch (NullPointerException | InputException e) {
            return new Message(CommandsEnum.RESPONSE_ERR, e.getMessage());
        }

        Message response = CommandToMessageAdapter.getMessage(command);

        logger.exiting(className, "runCommand", response);

        return response;
    }
}
