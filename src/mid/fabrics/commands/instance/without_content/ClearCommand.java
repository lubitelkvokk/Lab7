package mid.fabrics.commands.instance.without_content;


import server.collection.manager.CollectionManager;
import mid.commands.CommandResultType;
import mid.commands.CommandsEnum;
import mid.fabrics.commands.instance.outputI.CommandResultTypeString;

public class ClearCommand implements CommandWithoutData, CommandResultTypeString {
    private CollectionManager collectionManager;
    private CommandsEnum name = CommandsEnum.CLEAR;



    private String result;

    private CommandResultType commandResultType = CommandResultType.STRING;
    private final String DESCRIPTION = "clear : очистить коллекцию";
    @Override
    public String getDescription() {
        return DESCRIPTION;
    }

    @Override
    public void execute() {
        result = collectionManager.clear();
    }

    @Override
    public CommandsEnum getName() {
        return name;
    }

    @Override
    public CommandResultType getCommandResultType() {
        return commandResultType;
    }
    public void setCollectionManager(CollectionManager collectionManager) {
        this.collectionManager = collectionManager;
    }

    @Override
    public String getResult() {
        return result;
    }
}
