package com.mini.smartroad;

import com.mini.smartroad.model.*;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.AnnotationConfiguration;

public class HibernateUtils {

    private static final SessionFactory sessionFactory = buildSessionFactory();

    private static SessionFactory buildSessionFactory() {
        AnnotationConfiguration cfg = new AnnotationConfiguration().configure();
        cfg.addAnnotatedClass(UserEntity.class);
        cfg.addAnnotatedClass(UserPreferencesEntity.class);
        cfg.addAnnotatedClass(AddressEntity.class);
        cfg.addAnnotatedClass(StationEntity.class);
        cfg.addAnnotatedClass(StationStrategyEntity.class);
        return cfg.buildSessionFactory();
    }

    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }
}
