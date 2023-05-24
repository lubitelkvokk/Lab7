package mid.fabrics.commands.instance.with_content.with_string;

import mid.commands.CommandResultType;
import mid.commands.CommandsEnum;
import mid.data.User;
import mid.fabrics.commands.instance.outputI.CommandResultTypeString;
import mid.logger.ColorLogger;
import server.collection.manager.CollectionManager;

/**
 * Deprecated class because must be realised on client_processing module
 */
public class ExecuteScriptCommand implements CommandWithString, CommandResultTypeString {

    private CollectionManager collectionManager;
    private CommandsEnum name = CommandsEnum.EXECUTE_SCRIPT;

    private String result;

    private CommandResultType commandResultType = CommandResultType.STRING;

    private final String DESCRIPTION = ColorLogger.logErr(
            "execute_script file_name : считать и исполнить скрипт из указанного файла. " +
                    "В скрипте содержатся команды в таком же виде, в котором их вводит");
    private String data;

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
    public void execute() {
        result = null;
    }

    @Override
    public CommandResultType getCommandResultType() {
        return commandResultType;
    }
    @Override
    public void setData(String data) {
        this.data = data;
    }

    @Override
    public String getResult() {
        return result;
    }
}
