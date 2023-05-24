package server.handler.client_processing;

import mid.commands.CommandsEnum;
import mid.data.User;
import mid.fabrics.message.instance.Message;
import server.collection.db.services.user.UserService;

import java.sql.SQLException;

public class UserAuth {

    public static boolean handMessage(Message message) throws SQLException {
        User user = message.getUser();
        if (user == null) {
            return false;
        }
        UserService userService = new UserService();
        if (message.getCommand().equals(CommandsEnum.REGISTER_USER)) {
            return userService.registerUser(user);
        }
        return userService.loginUser(user);

    }
}
