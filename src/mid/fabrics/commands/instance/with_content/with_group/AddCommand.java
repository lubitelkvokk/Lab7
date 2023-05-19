package mid.fabrics.commands.instance.with_content.with_group;


import server.collection.manager.CollectionManager;
import mid.commands.CommandResultType;
import mid.commands.CommandsEnum;
import mid.data.StudyGroup;
import mid.fabrics.commands.instance.outputI.CommandResultTypeString;
import server.exceptions.InputException;

public class AddCommand implements CommandWithGroup, CommandResultTypeString {
    private CollectionManager collectionManager;
    private CommandsEnum name = CommandsEnum.ADD;

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
        result =  collectionManager.add(studyGroup);
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
