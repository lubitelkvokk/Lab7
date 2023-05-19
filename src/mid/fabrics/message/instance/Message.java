package mid.fabrics.message.instance;

import mid.commands.CommandsEnum;
import mid.data.StudyGroup;

import java.io.Serializable;
import java.util.LinkedList;

public class Message implements Serializable {

    CommandsEnum command;
    StudyGroup studyGroup;

    LinkedList<StudyGroup> studyGroups;
    String data;

    Integer id;

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

    public CommandsEnum getCommand() {
        return command;
    }



    public StudyGroup getStudyGroup() {
        return studyGroup;
    }

    public String getData() {
        return data;
    }

    public void setCommand(CommandsEnum command) {
        this.command = command;
    }

    public void setStudyGroup(StudyGroup studyGroup) {
        this.studyGroup = studyGroup;
    }


    public LinkedList<StudyGroup> getStudyGroups() {
        return studyGroups;
    }
    public void setStudyGroups(LinkedList<StudyGroup> studyGroups) {
        this.studyGroups = studyGroups;
    }

    public void setData(String data) {
        this.data = data;
    }

    public Message(CommandsEnum command, Integer id) {
        this.command = command;
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
