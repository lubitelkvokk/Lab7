package client.script;

import client.executor.CommandReader;
import client.exeptions.InputException;
import client.readers.IReader;
import client.readers.Reader;
import mid.commands.CommandsEnum;
import mid.fabrics.commands.CommandFabric;
import mid.fabrics.message.instance.Message;
import mid.fabrics.message.instance.MessageType;

import java.io.IOException;
import java.util.LinkedList;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class ScriptManager {

    static LinkedList<Message> messages = new LinkedList<>();
    static LinkedList<String> scripts = new LinkedList<>();
    private IReader reader;

    public ScriptManager(IReader reader) {
        this.reader = reader;
    }

    public LinkedList<Message> getMessagesFromFile(String path) throws IOException {
        scripts.add(path);
        String content = reader.readFromFile(path);
        IReader tempReader = reader;
        reader = new Reader(new Scanner(content));
        CommandReader commandReader = new CommandReader(reader);
        while (reader.hasNextLine()) {
            try {
                Message message = commandReader.getMessage();

                if (message.getCommand().equals(CommandsEnum.EXECUTE_SCRIPT)) {
                    ScriptManager scriptManager = new ScriptManager(reader);
                    if (!getScripts().contains(message.getData())) {
                        scriptManager.getMessagesFromFile(message.getData());
                    }
                } else {
                    if (message.getData() != null || message.getId() != null || message.getStudyGroup() != null
                            || MessageType.getType(CommandFabric.createCommand(message).getName()) == null) {
                        if (message.getStudyGroup() == null || message.getStudyGroup().check())
                            messages.add(message);
                    }
                }
            } catch (InputException | NoSuchElementException e) {
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        scripts.removeLast();
        reader = tempReader;
        return messages;
    }

    public LinkedList<Message> getHistory() {
        return messages;
    }

    public LinkedList<String> getScripts() {
        return scripts;
    }


    public void clearHistory(){
        messages.clear();
    }
}
