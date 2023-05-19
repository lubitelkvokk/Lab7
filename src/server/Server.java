package server;

import server.collection.converter.ParsingCollection;
import server.exceptions.InputException;
import server.handler.client_processing.ClientHandler;
import server.collection.checker.CollectionChecker;
import server.collection.manager.CollectionManager;
import mid.fabrics.commands.instance.Command;
import mid.fabrics.commands.instance.with_content.with_group.AddCommand;
import mid.fabrics.commands.instance.with_content.with_group.AddIfMinCommand;
import mid.fabrics.commands.instance.with_content.with_group.RemoveGreaterCommand;
import mid.fabrics.commands.instance.with_content.with_group.UpdateCommand;
import mid.fabrics.commands.instance.with_content.with_int.RemoveByIdCommand;
import mid.fabrics.commands.instance.without_content.*;
import server.handler.console.ConsoleHandler;

import java.util.logging.Level;
import java.util.logging.Logger;


public class Server {

    private static final Logger logger = Logger.getLogger(Server.class.getName());
    public static void main(String[] args) {
        Command[] commands = new Command[]{new AddCommand(), new AddIfMinCommand(),
                new UpdateCommand(), new RemoveGreaterCommand(), new RemoveByIdCommand(),
                new AverageOfShouldBeExpelledCommand(), new SumExpelledCommand(), new InfoCommand(),
                new MinByNameCommand(), new ShowCommand(), new ClearCommand(),
                new RemoveFirstCommand(), new HelpCommand()};

        ParsingCollection parsingCollection = new ParsingCollection();
        try {
            CollectionManager collectionManager = new CollectionManager(
                    commands,
                    parsingCollection.parseCollectionFromFile(System.getenv("KVOKKA")));
            Thread thread = new Thread(new ConsoleHandler(collectionManager));
            thread.start();
            ClientHandler clientHandler = new ClientHandler(collectionManager, commands, Integer.parseInt(args[0].trim()));
            clientHandler.run();
        } catch (Exception e) {
            logger.warning(e.getMessage());
        }
        //Устанавливаем CollectionManager для всех команд и указываем какие команды разрешены для использования


    }
}