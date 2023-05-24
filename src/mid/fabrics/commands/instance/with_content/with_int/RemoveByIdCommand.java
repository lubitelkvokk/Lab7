package mid.fabrics.commands.instance.with_content.with_int;

import mid.commands.CommandResultType;
import mid.commands.CommandsEnum;
import mid.data.User;
import mid.fabrics.commands.instance.outputI.CommandResultTypeString;
import server.collection.manager.CollectionManager;
import server.exceptions.InputException;


public class RemoveByIdCommand implements CommandWithId, CommandResultTypeString {
    private CollectionManager collectionManager;
    private CommandsEnum name = CommandsEnum.REMOVE_BY_ID;

    private String result;

    private CommandResultType commandResultType = CommandResultType.STRING;

    private final String DESCRIPTION = "remove_by_id id : удалить элемент из коллекции по его id";
    private Integer id;

    private User user;
    @Override
    public void setUser(User user) {
        this.user = user;
    }
    @Override
    public void setCollectionManager(CollectionManager collectionManager) {
        this.collectionManager = collectionManager;
    }

    @Override
    public CommandsEnum getName() {
        return name;
    }

    @Override
    public String getDescription() {
        return DESCRIPTION;
    }


    @Override
    public void execute() throws InputException {
        result = collectionManager.removeById(user,id);
    }

    @Override
    public CommandResultType getCommandResultType() {
        return commandResultType;
    }
    @Override
    public void setData(Integer id) {
        this.id = id;
    }

    @Override
    public String getResult() {
        return result;
    }
}
