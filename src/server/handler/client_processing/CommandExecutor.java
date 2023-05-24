package server.handler.client_processing;

import mid.adapters.CommandToMessageAdapter;
import mid.commands.CommandsEnum;
import mid.fabrics.commands.instance.Command;
import mid.fabrics.message.instance.Message;
import server.collection.manager.CollectionManager;
import server.exceptions.InputException;

import java.util.Arrays;
import java.util.logging.Logger;

public class CommandExecutor {
    private final Command[] commands;

    private static final String className = CommandExecutor.class.getName();
    private static final Logger logger = Logger.getLogger(className);


    public CommandExecutor(Command[] commands) {
        this.commands = commands;
    }

    public Message runCommand(Command command) throws InputException {

        try {
            command.setCollectionManager(CollectionManager.getInstance());
        } catch (InputException e) {
            return new Message(CommandsEnum.RESPONSE_ERR, e.getMessage());
        } catch (Exception e) {
            logger.warning(e.getMessage());
        }

        try {
            command.execute();
        } catch (NullPointerException | InputException e) {
            return new Message(CommandsEnum.RESPONSE_ERR, e.getMessage());
        }
        return CommandToMessageAdapter.getMessage(command);
    }
}
