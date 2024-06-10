package jm.task.core.jdbc.util;
import org.hibernate.SessionFactory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Util {
    // реализуйте настройку соеденения с БД
    public static final String DIALECT = "org.hibernate.dialect.MySQL5InnoDBDialect";
    private static final String HOST_NAME = "jdbc:mysql://localhost:3306";
    private static final String USER_NAME = "root";
    private static final String PASSWORD = "root";

    public static Connection getConnection() {
        Connection connection = null;
        try {
            connection =  DriverManager.getConnection(HOST_NAME, USER_NAME, PASSWORD);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return connection;
    }
}
