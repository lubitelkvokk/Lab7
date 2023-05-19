package client;


import client.exeptions.InputException;
import mid.commands.CommandsEnum;
//Invoker

/**
 * Класс, получающий в распоряжение команды, которые он может выполнять.
 */
public class ClientReceiver {
    /**
     * Команды, вводимые пользователем
     */
    private CommandsEnum[] commands;




    public void runCommand(String msg) throws InputException {
        CommandsEnum command = searchCommand(msg.trim());
        // TODO проверка на отсутствие команды
//        System.out.println(command);
    }

    public CommandsEnum searchCommand(String msg) {
        for (CommandsEnum x:CommandsEnum.values()){
            if (x.getName().equals(msg)){
                return x;
            }
        }
        return null;
    }

}
