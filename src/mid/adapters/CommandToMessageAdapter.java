package mid.adapters;

import mid.commands.CommandsEnum;
import mid.fabrics.commands.instance.Command;
import mid.fabrics.commands.instance.outputI.CommandResultTypeInt;
import mid.fabrics.commands.instance.outputI.CommandResultTypeString;
import mid.fabrics.commands.instance.outputI.CommandResultTypeStudyGroup;
import mid.fabrics.commands.instance.outputI.CommandResultTypeStudyGroups;
import mid.fabrics.message.MessageFabric;
import mid.fabrics.message.instance.Message;

public class CommandToMessageAdapter {

    public static Message getMessage(Command command) {
        switch (command.getCommandResultType()) {
            case STRING:
                CommandResultTypeString cmd1 = (CommandResultTypeString) command;
                return MessageFabric.createMessage(CommandsEnum.RESPONSE_TEXT, cmd1.getResult());
            case STUDY_GROUP:
                CommandResultTypeStudyGroup cmd2 = (CommandResultTypeStudyGroup) command;
                return MessageFabric.createMessage(CommandsEnum.RESPONSE_STUDY_GROUP, cmd2.getResult());
            case STUDY_GROUPS:
                CommandResultTypeStudyGroups cmd3 = (CommandResultTypeStudyGroups) command;
                return MessageFabric.createMessage(CommandsEnum.RESPONSE_STUDY_GROUPS, cmd3.getResult());
            case INT:
                CommandResultTypeInt cmd4 = (CommandResultTypeInt) command;
                return MessageFabric.createMessage(CommandsEnum.RESPONSE_INT, cmd4.getResult());
        }
        return MessageFabric.createMessage("Ошибка выполнения(PS. CommandMessageAdapter)");
    }
}
