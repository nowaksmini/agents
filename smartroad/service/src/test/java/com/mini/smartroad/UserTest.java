package com.mini.smartroad;

import com.mini.smartroad.model.UserEntity;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

public class UserTest {

    private static SessionFactory sessionFactory;

    @Before
    public void configure() {
        sessionFactory = HibernateUtils.getSessionFactory();
    }

    @Test
    public void testCreateUser() {
        UserEntity userEntity = TestBuilder.buildUser();
        String token = userEntity.getToken();
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        session.save(userEntity);
        transaction.commit();
        session.close();
        Session session1 = sessionFactory.openSession();
        List foundUsers = session1.createCriteria(UserEntity.class).add(Restrictions.eq("token", token)).list();
        Assert.assertNotNull(foundUsers);
        Assert.assertEquals(1, foundUsers.size());
        session1.beginTransaction();
        session1.delete(foundUsers.get(0));
        session1.getTransaction().commit();
        session1.close();
    }

    @After
    public void clear() {
        sessionFactory.close();
    }
}
