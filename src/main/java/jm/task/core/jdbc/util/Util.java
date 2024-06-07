package jm.task.core.jdbc.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Util {
    // реализуйте настройку соеденения с БД
    private static final String hostName = "jdbc:mysql://localhost:3306";
    private static final String userName = "root";
    private static final String password = "root";

    public static Connection getConnection() throws SQLException {
        return getConnection(hostName, userName, password);
    }

    public static Connection getConnection(String hostName, String userName, String password) throws SQLException {
        return DriverManager.getConnection(hostName, userName, password);
    }
}
