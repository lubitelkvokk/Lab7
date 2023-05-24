package mid.fabrics.commands.instance.without_content;


import mid.commands.CommandResultType;
import mid.commands.CommandsEnum;
import mid.data.User;
import mid.fabrics.commands.instance.outputI.CommandResultTypeInt;
import server.collection.manager.CollectionManager;

public class SumExpelledCommand implements CommandWithoutData, CommandResultTypeInt {
    private CollectionManager collectionManager;
    private CommandsEnum name = CommandsEnum.SUM_OF_SHOULD_BE_EXPELLED;

    private final String DESCRIPTION = "sum_of_should_be_expelled : вывести сумму значений поля shouldBeExpelled для всех элементов коллекции";

    private Integer result;
    private final CommandResultType commandResultType = CommandResultType.INT;

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
        result = collectionManager.sumOfShouldBeExpelled(user);
    }

    @Override
    public CommandsEnum getName() {
        return name;
    }


    public void setCollectionManager(CollectionManager collectionManager) {
        this.collectionManager = collectionManager;
    }

    @Override
    public CommandResultType getCommandResultType() {
        return commandResultType;
    }

    @Override
    public Integer getResult() {
        return result;
    }
}
