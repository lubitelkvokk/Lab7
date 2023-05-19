package mid.fabrics.commands.instance;


import mid.commands.CommandResultType;
import mid.commands.CommandsEnum;
import server.collection.manager.CollectionManager;
import server.exceptions.InputException;

public interface Command {


    void setCollectionManager(CollectionManager collectionManager);

    CommandResultType getCommandResultType();
    CommandsEnum getName();

    String getDescription();

    void execute() throws InputException;

}
