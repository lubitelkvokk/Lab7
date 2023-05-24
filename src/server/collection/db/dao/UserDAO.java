package server.collection.db.dao;


import mid.data.User;

public interface UserDAO {
    boolean registerUser(User user);

    boolean loginUser(User user);

    boolean userExistence(User user);
}
