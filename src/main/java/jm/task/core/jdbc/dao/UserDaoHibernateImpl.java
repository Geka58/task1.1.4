package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import javax.persistence.criteria.CriteriaQuery;
import java.util.ArrayList;
import java.util.List;

public class UserDaoHibernateImpl implements UserDao {
    public static SessionFactory sessionFactory = Util.getConnection();

    public UserDaoHibernateImpl() {

    }

    @Override
    public void createUsersTable() {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        try {
            session.createNativeQuery("CREATE TABLE IF NOT EXISTS `hibernate1.1.4`.`user` " +
                    "(`id` BIGINT NOT NULL AUTO_INCREMENT," +
                    "`name` VARCHAR(45) NOT NULL," +
                    "`last_name` VARCHAR(45) NOT NULL," +
                    "`age` TINYINT NOT NULL," +
                    "PRIMARY KEY (`id`))").executeUpdate();
            transaction.commit();
            System.out.println("Таблица создана.");
        } catch (HibernateException e) {
            System.err.println("Не удалось создать таблицу.");
            e.printStackTrace();
            if (transaction != null) {
                transaction.rollback();
            }
        } finally {
            session.close();
        }

    }

    @Override
    public void dropUsersTable() {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        try {
            session.createNativeQuery("DROP TABLE IF EXISTS user")
                    .executeUpdate();
            transaction.commit();
            System.out.println("Таблица удалена.");
        } catch (HibernateException e) {
            System.err.println("Не удалось удалить таблицу.");
            e.printStackTrace();
            if (transaction != null) {
                transaction.rollback();
            }
        } finally {
            session.close();
        }
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        try {
            session.save(new User(name, lastName, age));
            transaction.commit();
            System.out.println("Пользователь добавлен: " + name);
        } catch (HibernateException e) {
            System.err.println("Не удалось добавить пользователя");
            e.printStackTrace();
            if (transaction != null) {
                transaction.rollback();
            }
        } finally {
            session.close();
        }
    }

    @Override
    public void removeUserById(long id) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        try {
            session.delete(session.get(User.class, id));
            transaction.commit();
            System.out.println("user с id " + id + " удален");
        } catch (HibernateException e) {
            System.err.println("Не удалось удалить user с id = " + id);
            e.printStackTrace();
        } finally {
            session.close();
        }

    }

    @Override
    public List<User> getAllUsers() {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        CriteriaQuery<User> user = session.getCriteriaBuilder().createQuery(User.class);
        user.from(User.class);
        List<User> userList = session.createQuery(user).getResultList();
        try {
            transaction.commit();
        } catch (HibernateException e) {
            System.err.println("Не удалось");
            e.printStackTrace();
        } finally {
            session.close();
        }
        return userList;
    }

    @Override
    public void cleanUsersTable() {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        try {
            session.createNativeQuery("TRUNCATE TABLE `hibernate1.1.4`.`user`")
                    .executeUpdate();
            transaction.commit();
            System.out.println("Таблица очищена.");
        } catch (HibernateException e) {
            System.err.println("Не удалось очистить таблицу.");
            e.printStackTrace();
        } finally {
            session.close();
        }
    }
}
