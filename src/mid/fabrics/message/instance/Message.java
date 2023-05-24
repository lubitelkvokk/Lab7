package mid.fabrics.message.instance;

import lombok.Data;
import mid.commands.CommandsEnum;
import mid.data.StudyGroup;
import mid.data.User;

import java.io.Serializable;
import java.util.LinkedList;



@Data
public class Message implements Serializable {

    private User user;
    private CommandsEnum command;
    private StudyGroup studyGroup;

    private LinkedList<StudyGroup> studyGroups;
    private String data;

    private Integer id;



    public Message(CommandsEnum command) {
        this.command = command;
    }

    public Message(CommandsEnum command, StudyGroup studyGroup) {
        this(command);
        this.studyGroup = studyGroup;
    }

    public Message(CommandsEnum command, String data) {
        this(command);
        this.data = data;
    }

    public Message(CommandsEnum command, LinkedList<StudyGroup> studyGroups) {
        this(command);
        this.studyGroups = studyGroups;
    }


    public Message() {
    }

    public Message(CommandsEnum command, Integer id) {
        this.command = command;
        this.id = id;
    }

}
