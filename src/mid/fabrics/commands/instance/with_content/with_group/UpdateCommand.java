package mid.fabrics.commands.instance.with_content.with_group;


import mid.commands.CommandResultType;
import mid.commands.CommandsEnum;
import mid.data.StudyGroup;
import mid.fabrics.commands.instance.outputI.CommandResultTypeString;
import server.collection.manager.CollectionManager;

public class UpdateCommand implements CommandWithGroup, CommandResultTypeString {
    private CollectionManager collectionManager;
    private CommandsEnum name = CommandsEnum.UPDATE;
    private String result;

    private CommandResultType commandResultType = CommandResultType.STRING;
    private final String DESCRIPTION = "update id {element} : обновить значение элемента коллекции, id которого равен заданному";
    private StudyGroup studyGroup;

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
        result = collectionManager.update(studyGroup);
    }

    @Override
    public void setCollectionManager(CollectionManager collectionManager) {
        this.collectionManager = collectionManager;
    }
    @Override
    public CommandResultType getCommandResultType() {
        return commandResultType;
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
