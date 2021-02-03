package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import org.hibernate.Session;

import java.util.ArrayList;
import java.util.List;

public class UserDaoHibernateImpl extends Util implements UserDao {

    public UserDaoHibernateImpl() {

    }


    @Override
    public void createUsersTable() {
        try {
            Session session = getSessionFactory().openSession();
            session.beginTransaction();
            session.createSQLQuery("CREATE TABLE IF NOT EXISTS users (" +
                    "id INTEGER not NULL AUTO_INCREMENT," +
                    "name VARCHAR(255) not NULL," +
                    "lastName VARCHAR(255) not NULL," +
                    "age INTEGER not NULL," +
                    "PRIMARY KEY (id))").executeUpdate();
            session.getTransaction().commit();
            session.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            getSessionFactory().close();
        }
    }

    @Override
    public void dropUsersTable() {
        try {
            Session session = getSessionFactory().openSession();
            session.beginTransaction();
            session.createSQLQuery("DROP TABLE IF EXISTS users").executeUpdate();
            session.getTransaction().commit();
            session.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            getSessionFactory().close();
        }
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        try {
            Session session = getSessionFactory().openSession();
            session.beginTransaction();
            session.save(new User(name, lastName, age));
            session.getTransaction().commit();
            System.out.println("User с именем – " + name + " добавлен в базу данных");
            session.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            getSessionFactory().close();
        }
    }

    @Override
    public void removeUserById(long id) {
        try {
            Session session = getSessionFactory().openSession();
            session.beginTransaction();
            User user = (User) session.get(User.class, id);
            session.delete(user);
            session.getTransaction().commit();
            session.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            getSessionFactory().close();
        }
    }

    @Override
    public List<User> getAllUsers() {
        List<User> userList = null;
        try {
            Session session = getSessionFactory().openSession();
            session.beginTransaction();
            userList = session.createQuery("from User").list();
            session.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            getSessionFactory().close();
        }
        return userList;
    }

    @Override
    public void cleanUsersTable() {
        try {
            Session session = getSessionFactory().openSession();
            session.beginTransaction();
            session.createQuery("delete User").executeUpdate();
            session.getTransaction().commit();
            session.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            getSessionFactory().close();
        }
    }
}
