package mid.fabrics.commands.instance.without_content;


import mid.commands.CommandResultType;
import mid.commands.CommandsEnum;
import mid.data.User;
import mid.fabrics.commands.instance.outputI.CommandResultTypeString;
import server.collection.manager.CollectionManager;

public class ExitCommand implements CommandWithoutData, CommandResultTypeString {
    private CollectionManager collectionManager;
    private CommandsEnum name = CommandsEnum.EXIT;
    private String result;

    private CommandResultType commandResultType = CommandResultType.STRING;
    private final String DESCRIPTION = "exit : завершить программу (без сохранения в файл)";
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
    public void execute() {
        result = collectionManager.exit();
    }

    public void setCollectionManager(CollectionManager collectionManager) {
        this.collectionManager = collectionManager;
    }

    @Override
    public CommandResultType getCommandResultType() {
        return commandResultType;
    }
    @Override
    public CommandsEnum getName() {
        return name;
    }


    @Override
    public String getResult() {
        return result;
    }
}
