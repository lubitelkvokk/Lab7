package server.handler.client_processing;

import mid.commands.CommandsEnum;
import mid.fabrics.commands.CommandFabric;
import mid.fabrics.commands.instance.Command;
import mid.fabrics.commands.instance.with_content.with_group.AddCommand;
import mid.fabrics.commands.instance.with_content.with_group.AddIfMinCommand;
import mid.fabrics.commands.instance.with_content.with_group.RemoveGreaterCommand;
import mid.fabrics.commands.instance.with_content.with_group.UpdateCommand;
import mid.fabrics.commands.instance.with_content.with_int.RemoveByIdCommand;
import mid.fabrics.commands.instance.without_content.*;
import mid.fabrics.message.instance.Message;
import server.collection.db.services.user.UserService;
import server.collection.manager.CollectionManager;
import server.handler.client_processing.message.getting.MessageGetter;
import server.handler.client_processing.message.sending.MessageSender;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketAddress;
import java.nio.channels.DatagramChannel;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.logging.Logger;

public class NewClientHandler implements Runnable {

    private DatagramChannel server;
    private byte[] data;

    private static final Logger logger = Logger.getLogger(NewClientHandler.class.getName());

    private SocketAddress client;

    public NewClientHandler(DatagramChannel datagramChannel, byte[] data, SocketAddress client) {
        this.server = datagramChannel;
        this.data = data;
        this.client = client;
    }

    @Override
    public void run() {
        try {
            Message request = MessageGetter.getMessage(data);

            // Эти условия можно было бы заменить на паттерн последовательной обработки событий

            if (!UserAuth.handMessage(request)) {
                MessageSender.sendMessage(server, new Message(CommandsEnum.RESPONSE_ERR,
                        "Ошибка аутентификации пользователя\n" +
                                "Авторизация: login\n" +
                                "Регистрация: register\n"), client);

            } else if (request.getCommand().equals(CommandsEnum.LOGIN_USER)) {
                MessageSender.sendMessage(server, new Message(CommandsEnum.RESPONSE_TEXT,
                        "Авторизация прошла успешно"), client);
            } else if (request.getCommand().equals(CommandsEnum.REGISTER_USER)) {
                MessageSender.sendMessage(server, new Message(CommandsEnum.RESPONSE_TEXT,
                        "Регистрация прошла успешно"), client);
            } else {
                request.getUser().setId(new UserService().getUserId(request.getUser()));
                logger.info(request.getUser().getLogin() + " is served");
                CommandExecutor commandExecutor = new CommandExecutor(new Command[]{new AddCommand(), new AddIfMinCommand(),
                        new UpdateCommand(), new RemoveGreaterCommand(), new RemoveByIdCommand(),
                        new AverageOfShouldBeExpelledCommand(), new SumExpelledCommand(), new InfoCommand(),
                        new MinByNameCommand(), new ShowCommand(), new ClearCommand(),
                        new RemoveFirstCommand(), new HelpCommand()});

                Command command = CommandFabric.createCommand(request);
                command.setUser(request.getUser());

                Message response = commandExecutor.runCommand(command);

//                System.out.println(response.getStudyGroups());
                MessageSender.sendMessage(server, response, client);
            }

        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        } catch (SQLException e) {
            try {
                logger.warning(e.getMessage());
                MessageSender.sendMessage(server, new Message(CommandsEnum.RESPONSE_ERR,
                        "databaseError"), client);
            } catch (IOException | ClassNotFoundException ex) {
                throw new RuntimeException(ex);
            }
        }

    }
}
