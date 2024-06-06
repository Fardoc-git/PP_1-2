package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {
    public UserDaoJDBCImpl() {

    }

    public void createUsersTable() {
        String expression = """
                CREATE TABLE dbtest.users
                (
                    id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
                    name VARCHAR(45) NOT NULL,
                    lastName VARCHAR(45) NOT NULL,
                    age INT(3) NULL
                )
                    ENGINE = InnoDB
                    DEFAULT CHARACTER SET = utf8;
                """;

        try (Connection connection = Util.getConnection()) {
            Statement statement = connection.createStatement();
            statement.executeUpdate(expression);

            System.out.println("table created");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void dropUsersTable() {
        String expression = "DROP TABLE dbtest.users;";

        try (Connection connection = Util.getConnection()) {
            Statement statement = connection.createStatement();
            statement.executeUpdate(expression);

            System.out.println("table deleted");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void saveUser(String name, String lastName, byte age) {
        String expression = "INSERT INTO dbtest.users (name, lastName, age) VALUES (?, ?, ?);";

        try (Connection connection = Util.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(expression);
            statement.setString(1, name);
            statement.setString(2, lastName);
            statement.setByte(3, age);
            statement.executeUpdate();

            System.out.printf("User с именем - %s добавлен в базу данных\n", name);
        } catch (SQLException e) {
//            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }

    public void removeUserById(long id) {
        String expression = "DELETE FROM dbtest.users WHERE id = ?;";

        try (Connection connection = Util.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(expression);
            statement.setLong(1, id);
            statement.executeUpdate();

            System.out.println("deleted by id");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

    }

    public List<User> getAllUsers() {
        String expression = "SELECT * FROM dbtest.users;";
        ResultSet rs = null;
        List<User> userList = new ArrayList<>();

        try (Connection connection = Util.getConnection()) {
            Statement statement = connection.createStatement();
            rs = statement.executeQuery(expression);
            while (rs.next()) {
                User user = new User(rs.getString("name")
                        ,rs.getString("lastName")
                        ,rs.getByte("age"));
                user.setId(rs.getLong("id"));
                userList.add(user);
            }

            System.out.println("got all users");
        } catch (SQLException | NullPointerException e) {
            System.out.println(e.getMessage());
        }

        return userList;
    }

    public void cleanUsersTable() {
        String expression = "DELETE FROM dbtest.users;";

        try (Connection connection = Util.getConnection()) {
            Statement statement = connection.createStatement();
            statement.executeUpdate(expression);

            System.out.println("table cleared");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}
