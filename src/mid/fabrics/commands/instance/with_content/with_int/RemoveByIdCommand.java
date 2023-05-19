package mid.fabrics.commands.instance.with_content.with_int;

import server.collection.manager.CollectionManager;
import mid.commands.CommandResultType;
import mid.commands.CommandsEnum;
import mid.fabrics.commands.instance.outputI.CommandResultTypeString;


public class RemoveByIdCommand implements CommandWithId, CommandResultTypeString {
    private CollectionManager collectionManager;
    private CommandsEnum name = CommandsEnum.REMOVE_BY_ID;

    private String result;

    private CommandResultType commandResultType = CommandResultType.STRING;

    private final String DESCRIPTION = "remove_by_id id : удалить элемент из коллекции по его id";
    private Integer id;

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
    public void execute() {
        result = collectionManager.removeById(id);
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
