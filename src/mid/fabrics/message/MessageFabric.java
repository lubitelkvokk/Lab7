package mid.fabrics.message;

import mid.commands.CommandsEnum;
import mid.data.StudyGroup;
import mid.fabrics.message.instance.Message;

import java.util.LinkedList;

public class MessageFabric {

    public static Message createMessage(CommandsEnum command, StudyGroup studyGroup) {
        return new Message(command, studyGroup);
    }

    public static Message createMessage(CommandsEnum command, String data) {
        return new Message(command, data);
    }

    public static Message createMessage(CommandsEnum command, Integer id) {
        return new Message(command, id);
    }

    public static Message createMessage(CommandsEnum command) {
        return new Message(command);
    }

    public static Message createMessage(CommandsEnum command, LinkedList<StudyGroup> studyGroups){
        return new Message(command, studyGroups);
    }

    public static Message createMessage(String data){
        return new Message(CommandsEnum.RESPONSE_TEXT, data);
    }
}
