package server.collection.db;

import server.collection.db.entities.User;
import server.collection.db.services.user.HashPasswordBuilder;
import server.collection.db.services.user.UserService;
import server.collection.db.util.DbConnector;

import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;

public class Start {
    public static void main(String[] args) {
        try {
            DbConnector.getConnection();
            UserService userService = new UserService();
            User user = new User("ALEG", "GOODWORK");
            if (!userService.userExistence(user)){
                userService.registerUser(user);
            } else{
                System.out.println("Login is taken");
            }

        } catch (SQLException e){
            System.out.println(e.getMessage());
        }
    }
}
