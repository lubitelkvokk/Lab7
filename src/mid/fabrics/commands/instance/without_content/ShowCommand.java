package mid.fabrics.commands.instance.without_content;


import mid.commands.CommandResultType;
import mid.commands.CommandsEnum;
import mid.data.StudyGroup;
import mid.data.User;
import mid.fabrics.commands.instance.outputI.CommandResultTypeStudyGroups;
import server.collection.manager.CollectionManager;

import java.util.LinkedList;

public class ShowCommand implements CommandWithoutData, CommandResultTypeStudyGroups {
    private CollectionManager collectionManager;
    private CommandsEnum name = CommandsEnum.SHOW;

    private LinkedList<StudyGroup> result;

    private CommandResultType commandResultType = CommandResultType.STUDY_GROUPS;
    private final String DESCRIPTION = "show : вывести в стандартный поток вывода все элементы коллекции в строковом представлении";

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
        result = collectionManager.show(user);
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
    public CommandsEnum getName() {
        return name;
    }


    @Override
    public LinkedList<StudyGroup> getResult() {
        return result;
    }
}
