package com.example.labdb.utils;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

/**
 * Create session factory
 */
public class HibernateUtil {

    private static final SessionFactory sessionFactory = getCurrentSessionFromConfig();

    private static SessionFactory getCurrentSessionFromConfig() {
        // SessionFactory in Hibernate 5 example
        Configuration config = new Configuration();
        config.configure();
        // local SessionFactory bean created
        SessionFactory sessionFactory = config.buildSessionFactory();
        return sessionFactory;
    }

    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }

}