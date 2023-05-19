package server.collection.db.dao;

import server.collection.db.entities.User;

public interface UserDAO {
    boolean registerUser(User user);

    boolean loginUser(User user);

    boolean userExistence(User user);
}
