package mid.fabrics.commands.instance.without_content;


import server.collection.manager.CollectionManager;
import mid.commands.CommandResultType;
import mid.commands.CommandsEnum;
import mid.data.StudyGroup;
import mid.fabrics.commands.instance.outputI.CommandResultTypeStudyGroup;

public class MinByNameCommand implements CommandWithoutData, CommandResultTypeStudyGroup {
    private CollectionManager collectionManager;
    private CommandsEnum name = CommandsEnum.MIN_BY_NAME;

    private StudyGroup result;

    private CommandResultType commandResultType = CommandResultType.STUDY_GROUP;;

    private final String DESCRIPTION = "min_by_name : вывести любой объект из коллекции, значение поля name которого является минимальным";
    @Override
    public String getDescription() {
        return DESCRIPTION;
    }

    @Override
    public void execute() {
        result = collectionManager.minByName();
    }

    @Override
    public CommandsEnum getName() {
        return name;
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
    public StudyGroup getResult() {
        return result;
    }
}
