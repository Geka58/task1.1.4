package jm.task.core.jdbc.util;

import jm.task.core.jdbc.model.User;
import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

public class Util {
    private static final String HOST = "jdbc:mysql://localhost:3306/hibernate1.1.4";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "root";
    private static final String DRIVE = "com.mysql.cj.jdbc.Driver";

    private static SessionFactory sessionFactory = null;

    public static SessionFactory getConnection() {
        try {
            Configuration configuration = new Configuration()
                    .setProperty("hibernate.connection.drive_class", DRIVE)
                    .setProperty("hibernate.connection.url", HOST)
                    .setProperty("hibernate.connection.username", USERNAME)
                    .setProperty("hibernate.connection.password", PASSWORD)
                    .setProperty("hibernate.dialect", "org.hibernate.dialect.MySQL5Dialect")
                    .setProperty("hibernate.show_sql", "true")
                    .addAnnotatedClass(User.class);

            ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
                    .applySettings(configuration.getProperties())
                    .build();
            sessionFactory = configuration.buildSessionFactory(serviceRegistry);

        } catch (HibernateException e) {
            e.printStackTrace();
        }
        return sessionFactory;
    } // реализуйте настройку соеденения с БД
}
