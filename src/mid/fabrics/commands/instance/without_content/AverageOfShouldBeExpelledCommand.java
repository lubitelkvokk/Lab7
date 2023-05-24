package mid.fabrics.commands.instance.without_content;


import mid.commands.CommandResultType;
import mid.commands.CommandsEnum;
import mid.data.User;
import mid.fabrics.commands.instance.outputI.CommandResultTypeInt;
import server.collection.manager.CollectionManager;

public class AverageOfShouldBeExpelledCommand implements CommandWithoutData, CommandResultTypeInt {
    private CollectionManager collectionManager;
    private CommandsEnum name = CommandsEnum.AVERAGE_OF_SHOULD_BE_EXPELLED;

    private Integer result;

    private CommandResultType commandResultType = CommandResultType.INT;
    private final String DESCRIPTION = "average_of_should_be_expelled : вывести среднее значение поля shouldBeExpelled для всех элементов коллекции";

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
    public void execute() {
        result = collectionManager.averageOfShouldBeExpelled(user);
    }

    @Override
    public Integer getResult() {
        return result;
    }
}
