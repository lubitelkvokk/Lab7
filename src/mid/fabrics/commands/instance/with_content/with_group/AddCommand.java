package mid.fabrics.commands.instance.with_content.with_group;


import mid.commands.CommandResultType;
import mid.commands.CommandsEnum;
import mid.data.StudyGroup;
import mid.data.User;
import mid.fabrics.commands.instance.outputI.CommandResultTypeString;
import server.collection.manager.CollectionManager;
import server.exceptions.InputException;

public class AddCommand implements CommandWithGroup, CommandResultTypeString {
    private CollectionManager collectionManager;
    private CommandsEnum name = CommandsEnum.ADD;

    private User user;
    private String result;

    private CommandResultType commandResultType = CommandResultType.STRING;
    private StudyGroup studyGroup;

    private final String DESCRIPTION = "add {element} : добавить новый элемент в коллекцию";

    @Override
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
    public String getDescription() {
        return DESCRIPTION;
    }

    @Override
    public void execute() throws InputException {
        result =  collectionManager.add(user, studyGroup);
    }

    @Override
    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public void setData(StudyGroup studyGroup) {
        this.studyGroup = studyGroup;
    }

    @Override
    public String getResult() {
        return result;
    }
}
