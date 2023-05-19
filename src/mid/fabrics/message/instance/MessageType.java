package mid.fabrics.message.instance;

import mid.commands.CommandsEnum;

public class MessageType {

    public static COMMAND_TYPE getType(CommandsEnum command) {
        if (command.equals(CommandsEnum.ADD) ||
                command.equals(CommandsEnum.REMOVE_GREATER) ||
                command.equals(CommandsEnum.ADD_IF_MIN) ||
                command.equals(CommandsEnum.UPDATE)) {
            return COMMAND_TYPE.STUDY_GROUP;
        } else if (command.equals(CommandsEnum.REMOVE_BY_ID)) {
            return COMMAND_TYPE.INT;
        } else if (command.equals(CommandsEnum.EXECUTE_SCRIPT)) {
            return COMMAND_TYPE.STRING;
        }
        return null;
    }




}
