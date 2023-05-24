package server.collection.db.services.user;

import mid.data.User;
import server.collection.db.dao.UserDAO;
import server.collection.db.util.DbConnector;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Logger;

public class UserService implements UserDAO {

    private static final Logger logger = Logger.getLogger(UserService.class.getName());
    private Connection connection = DbConnector.getConnection();

    public UserService() throws SQLException {
    }

    @Override
    public boolean registerUser(User user) {
        String sql = "INSERT INTO \"user\" (login,password) values(?,?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, user.getLogin());
            String password = HashPasswordBuilder.hashPassword(user.getPassword(), user.getLogin().getBytes());
            preparedStatement.setString(2, password);
            preparedStatement.executeUpdate();
            return true;
        } catch (Exception e) {
            logger.warning(e.getMessage());
            return false;
        }
    }

    @Override
    public boolean loginUser(User user) {
        String sql = "SELECT * FROM \"user\" WHERE login=? AND password=?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, user.getLogin());
            String password = HashPasswordBuilder.hashPassword(user.getPassword(), user.getLogin().getBytes());
            preparedStatement.setString(2, password);
            ResultSet resultSet = preparedStatement.executeQuery();
            return resultSet.isBeforeFirst();
        } catch (Exception e) {
            logger.warning(e.getMessage());
            return false;
        }
    }

    // TODO Нужно ли вообще делать отельный метод на проверку пользователя по логину?
    @Override
    public boolean userExistence(User user) {
        String sql = "SELECT * FROM \"user\" WHERE login=?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, user.getLogin());
            ResultSet resultSet = preparedStatement.executeQuery();
            return resultSet.isBeforeFirst();
        } catch (Exception e) {
            logger.warning(e.getMessage());
            return false;
        }
    }


    public int getUserId(User user) throws SQLException {
        String sql = "SELECT id FROM \"user\" WHERE login=?";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1, user.getLogin());
        ResultSet resultSet = preparedStatement.executeQuery();
        resultSet.next();
        return resultSet.getInt(1);
    }

}
