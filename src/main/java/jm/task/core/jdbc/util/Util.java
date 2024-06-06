package jm.task.core.jdbc.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Scanner;

public class Util {
    // реализуйте настройку соеденения с БД

    public static Connection getConnection() throws SQLException {
        String hostName = "jdbc:mysql://localhost:3306";
        String userName = "root";
//        Scanner scanner = new Scanner(System.in);
//        String password = scanner.nextLine();
        String password = "root";
        return getConnection(hostName, userName, password);
    }

    public static Connection getConnection(String hostName, String userName, String password) throws SQLException {
        return DriverManager.getConnection(hostName, userName, password);
    }
}
