package server.collection.db.services;

import server.collection.db.dao.UserDAO;
import server.collection.db.entities.User;
import server.collection.db.util.DbConnector;

import java.sql.Connection;
import java.sql.SQLException;

public class UserService implements UserDAO {
    private Connection connection = DbConnector.getConnection();

    public UserService() throws SQLException {
    }

    @Override
    public boolean registerUser(User user) {
        String sql = "INSERT INTO"
    }

    @Override
    public boolean loginUser(User user) {
        return false;
    }
}
