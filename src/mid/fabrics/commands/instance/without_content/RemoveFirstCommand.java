package mid.fabrics.commands.instance.without_content;


import mid.commands.CommandResultType;
import mid.commands.CommandsEnum;
import mid.data.User;
import mid.fabrics.commands.instance.outputI.CommandResultTypeString;
import server.collection.manager.CollectionManager;
import server.exceptions.InputException;

public class RemoveFirstCommand implements CommandWithoutData, CommandResultTypeString {
    private CollectionManager collectionManager;
    private CommandsEnum name = CommandsEnum.REMOVE_BY_ID;

    private String result;
    private CommandResultType commandResultType = CommandResultType.STRING;
    private final String DESCRIPTION = "remove_first : удалить первый элемент из коллекции";

    private User user;
    @Override
    public void setUser(User user) {
        this.user = user;
    }
    @Override
    public String getDescription() {
        return DESCRIPTION;
    }


    @Override
    public void execute() throws InputException {
        result = collectionManager.removeFirst(user);
    }

    @Override
    public CommandsEnum getName() {
        return name;
    }

    public void setCollectionManager(CollectionManager collectionManager) {
        this.collectionManager = collectionManager;
    }

    @Override
    public CommandResultType getCommandResultType() {
        return commandResultType;
    }

    @Override
    public String getResult() {
        return result;
    }
}
