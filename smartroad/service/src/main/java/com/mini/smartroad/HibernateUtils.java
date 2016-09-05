package com.mini.smartroad;

import com.mini.smartroad.model.*;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

public class HibernateUtils {

    private static final SessionFactory sessionFactory = buildSessionFactory();

    private static SessionFactory buildSessionFactory() {
        Configuration cfg = new Configuration().configure();
        cfg.addAnnotatedClass(UserEntity.class);
        cfg.addAnnotatedClass(AddressEntity.class);
        cfg.addAnnotatedClass(StationEntity.class);
        cfg.addAnnotatedClass(ActionEntity.class);
        cfg.addAnnotatedClass(CouponEntity.class);
        ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder().
                applySettings(cfg.getProperties()).build();
        return cfg.buildSessionFactory(serviceRegistry);
    }

    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }
}
