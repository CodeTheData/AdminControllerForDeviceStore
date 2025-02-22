package services;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnectService {

    public static final String URL = "jdbc:postgresql://localhost:5432/store";
    public static final String USER = "postgres";
    public static final String PASSWORD = "0092";

    private Connection connection;

    public DBConnectService() {
        try {
            this.connection = DriverManager.getConnection(URL,
                    USER, PASSWORD);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void closeConnection() throws SQLException {
        this.connection.close();
    }

    public Connection getConnection() {
        return connection;
    }
}
