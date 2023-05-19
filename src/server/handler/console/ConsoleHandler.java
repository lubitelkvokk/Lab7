package server.handler.console;

import mid.fabrics.commands.instance.Command;
import server.collection.manager.CollectionManager;

import java.util.Scanner;
import java.util.logging.Logger;

public class ConsoleHandler implements Runnable {

    private static final Logger logger = Logger.getLogger(ConsoleHandler.class.getName());
    private CollectionManager collectionManager;

    public ConsoleHandler(CollectionManager collectionManager) {
        this.collectionManager = collectionManager;
    }

    @Override
    public void run() {
        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNextLine()) {
            if (scanner.next().trim().equals("save")) {
                logger.info(collectionManager.save());
            }
        }
    }


}
