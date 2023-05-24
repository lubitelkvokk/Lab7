package mid.fabrics.commands;

import mid.commands.CommandsEnum;
import mid.fabrics.commands.instance.Command;
import mid.fabrics.commands.instance.with_content.with_group.AddCommand;
import mid.fabrics.commands.instance.with_content.with_group.AddIfMinCommand;
import mid.fabrics.commands.instance.with_content.with_group.RemoveGreaterCommand;
import mid.fabrics.commands.instance.with_content.with_group.UpdateCommand;
import mid.fabrics.commands.instance.with_content.with_int.RemoveByIdCommand;
import mid.fabrics.commands.instance.without_content.*;
import mid.fabrics.message.instance.Message;


/**
 * Class for creating commands by his name(CommandsEnum)
 * This class needed also for server because client_processing isn't allowed to start commands
 */
public class CommandFabric {

    /**
     * @param message
     * @return Command
     */
    public static Command createCommand(Message message) {
        CommandsEnum commandEnum = message.getCommand();
        switch (commandEnum) {
            case ADD:
                AddCommand addCommand = new AddCommand();
                addCommand.setData(message.getStudyGroup());
                return addCommand;
            case ADD_IF_MIN:
                AddIfMinCommand addIfMinCommand = new AddIfMinCommand();
                addIfMinCommand.setData(message.getStudyGroup());
                return addIfMinCommand;
            case REMOVE_GREATER:
                RemoveGreaterCommand removeGreaterCommand = new RemoveGreaterCommand();
                removeGreaterCommand.setData(message.getStudyGroup());
                return removeGreaterCommand;
            case UPDATE:
                UpdateCommand updateCommand = new UpdateCommand();
                updateCommand.setData(message.getStudyGroup());
                return updateCommand;
            case REMOVE_BY_ID:
                RemoveByIdCommand removeByIdCommand = new RemoveByIdCommand();
                removeByIdCommand.setData(message.getId());
                return removeByIdCommand;
            case AVERAGE_OF_SHOULD_BE_EXPELLED:
                return new AverageOfShouldBeExpelledCommand();
            case CLEAR:
                return new ClearCommand();
            case EXIT:
                return new ExitCommand();
            case HELP:
                return new HelpCommand();
            case INFO:
                return new InfoCommand();
            case MIN_BY_NAME:
                return new MinByNameCommand();
            case REMOVE_FIRST:
                return new RemoveFirstCommand();
            case SAVE:
                return new SaveCommand();
            case SHOW:
                return new ShowCommand();
            case SUM_OF_SHOULD_BE_EXPELLED:
                return new SumExpelledCommand();
        }
        return null;
    }
}
