package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;


import javax.persistence.Query;
import java.util.List;

public class UserDaoHibernateImpl implements UserDao {
    private static final SessionFactory sessionFactory = Util.getSessionFactory();

    public UserDaoHibernateImpl() {

    }

    @Override
    public void createUsersTable() {
        String expression = """
                CREATE TABLE IF NOT EXISTS dbtest.users
                (
                    id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
                    name VARCHAR(45) NOT NULL,
                    lastName VARCHAR(45) NOT NULL,
                    age INT(3) NULL
                )
                    ENGINE = InnoDB
                    DEFAULT CHARACTER SET = utf8;
                """;
        Session session = sessionFactory.openSession();
        try (session) {
            Transaction transaction = session.beginTransaction();
            Query query = session.createSQLQuery(expression)
                    .addEntity(User.class);
            query.executeUpdate();
            transaction.commit();
        } catch (HibernateException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void dropUsersTable() {
        String expression = "DROP TABLE IF EXISTS dbtest.users;";
        Session session = sessionFactory.openSession();
        try (session) {
            session.beginTransaction();
            Query query = session.createSQLQuery(expression)
                    .addEntity(User.class);
            query.executeUpdate();
            session.getTransaction().commit();
        } catch (HibernateException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        Session session = sessionFactory.openSession();
        try (session) {
            session.beginTransaction();
            User user = new User(name, lastName, age);
            session.save(user);
            session.getTransaction().commit();
        } catch (HibernateException e) {
            if (session.getTransaction().isActive()) {
                session.getTransaction().rollback();
            }
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void removeUserById(long id) {
        Session session = sessionFactory.openSession();
        try (session) {
            session.beginTransaction();
            session.remove(session.get(User.class, id));
            session.getTransaction().commit();
        } catch (HibernateException e) {
            if (session.getTransaction().isActive()) {
                session.getTransaction().rollback();
            }
            System.out.println(e.getMessage());
        }
    }

    @Override
    public List<User> getAllUsers() {
        Session session = sessionFactory.openSession();
        try (session) {
            return session.createQuery("from User", User.class).list();
        }
    }

    @Override
    public void cleanUsersTable() {
        Session session = sessionFactory.openSession();
        try (session) {
            session.beginTransaction();
            session.createQuery("delete from User").executeUpdate();
            session.getTransaction().commit();
        } catch (HibernateException e) {
            if (session.getTransaction().isActive()) {
                session.getTransaction().rollback();
            }
            System.out.println(e.getMessage());
        }
    }
}
