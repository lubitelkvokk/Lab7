package server.collection.db.util;

import java.sql.*;
import java.util.logging.Logger;

public class DbConnector {


    private static final Logger logger = Logger.getLogger(DbConnector.class.getName());
    private static final String url =
            "jdbc:postgresql://localhost:5432/studs";
    private static final String username = System.getenv("USERNAME");
    private static final String password = System.getenv("PASSWORD");

    public static Connection getConnection() throws SQLException {

        Connection connection = DriverManager.getConnection(url,
                username,
                password);
        return connection;
    }
}
