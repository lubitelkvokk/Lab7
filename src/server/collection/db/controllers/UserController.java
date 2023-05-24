package server.collection.db.controllers;

import mid.data.User;
import server.collection.db.services.user.HashPasswordBuilder;
import server.collection.db.services.user.UserService;

import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;

public class UserController {

    public boolean registerUser(User user) throws NoSuchAlgorithmException, SQLException {
        UserService userService = new UserService();
        return userService.registerUser(hashPasswordForUser(user));
    }

    public boolean loginUser(User user) throws SQLException, NoSuchAlgorithmException {
        UserService userService = new UserService();
        return userService.loginUser(hashPasswordForUser(user));
    }

    public boolean userExistence(User user) throws SQLException, NoSuchAlgorithmException {
        UserService userService = new UserService();
        return userService.userExistence(hashPasswordForUser(user));
    }


    public User hashPasswordForUser(User user) throws NoSuchAlgorithmException {
        String password = HashPasswordBuilder.hashPassword(user.getPassword(), user.getLogin().getBytes());
        user.setPassword(password);
        return user;
    }


}
