package main;

import main.entities.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import main.entities.User;

public class OwnSessionFactory {

    private static final SessionFactory sessionFactory = createSessionFactory();
    private static SessionFactory createSessionFactory(){
        Configuration configuration = new Configuration();
        ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder().applySettings(configuration.getProperties()).build();
        configuration.addAnnotatedClass(User.class);
        return configuration
                .buildSessionFactory(serviceRegistry);
    }
    public static SessionFactory getSessionFactory(){
        return sessionFactory;
    }

}