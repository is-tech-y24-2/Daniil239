package ru.itmo.kotiki.util;

import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import ru.itmo.kotiki.dao.entity.Cat;
import ru.itmo.kotiki.dao.entity.Owner;

import java.awt.*;

public class HibernateSessionFactoryUtil {

    private static SessionFactory sessionFactory;

    private HibernateSessionFactoryUtil(){
    }

    public static SessionFactory getSessionFactory(){
        if (sessionFactory == null){
            Configuration configuration = new Configuration().configure();

            configuration.addAnnotatedClass(Owner.class);
            configuration.addAnnotatedClass(Cat.class);
            configuration.addAnnotatedClass(Color.class);

            StandardServiceRegistryBuilder builder = new StandardServiceRegistryBuilder().applySettings(configuration.getProperties());
            sessionFactory = configuration.buildSessionFactory(builder.build());
        }
        return sessionFactory;
    }
}
