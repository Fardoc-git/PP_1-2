package jm.task.core.jdbc.util;

import jm.task.core.jdbc.model.User;
import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.Environment;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class Util {
    // реализуйте настройку соеденения с БД
    private static final String DRIVER = "com.mysql.cj.jdbc.Driver";
    public static final String DIALECT = "org.hibernate.dialect.MySQL5InnoDBDialect";
    private static final String HOST_NAME = "jdbc:mysql://localhost:3306";
    private static final String USER_NAME = "root";
    private static final String PASSWORD = "root";

    private static SessionFactory sessionFactory;

    public static Connection getConnection() {
        Connection connection = null;
        try {
            connection =  DriverManager.getConnection(HOST_NAME, USER_NAME, PASSWORD);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return connection;
    }
//
//    public static Connection getConnection(String hostName, String userName, String password) throws SQLException {
//        return DriverManager.getConnection(hostName, userName, password);
//    }

    public static SessionFactory getSessionFactory() {
        if (sessionFactory == null){
            try {
                Configuration configuration = new Configuration();
                Properties properties = getProperties();

                sessionFactory = configuration
                        .addAnnotatedClass(User.class)
                        .addProperties(properties)
                        .buildSessionFactory();
            } catch (HibernateException e) {
                System.out.println(e.getMessage());
            }
        }
        return sessionFactory;
    }

    private static Properties getProperties() {
        Properties properties = new Properties();

        properties.put(Environment.DRIVER, DRIVER);
        properties.put(Environment.URL, HOST_NAME);
        properties.put(Environment.USER, USER_NAME);
        properties.put(Environment.PASS, PASSWORD);

        properties.put(Environment.DIALECT, DIALECT);
        properties.put(Environment.SHOW_SQL, "true");
        properties.put(Environment.FORMAT_SQL, "true");

        properties.put(Environment.CURRENT_SESSION_CONTEXT_CLASS, "thread");
        properties.put(Environment.HBM2DDL_AUTO, "none");

        return properties;
    }
}
