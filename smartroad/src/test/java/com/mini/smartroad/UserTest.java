package com.mini.smartroad;

import com.mini.smartroad.common.CryptoUtils;
import com.mini.smartroad.model.UserEntity;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.List;

public class UserTest {

    private static SessionFactory sessionFactory;

    @BeforeClass
    public static void configure() {
        sessionFactory = HibernateUtil.getSessionFactory();
    }

    @Test
    public void testCreateUser() {
        UserEntity userEntity = new UserEntity();
        userEntity.setLastName("Nowak");
        userEntity.setFirstName("Sylwia");
        userEntity.setEmail("sylwia.nowak@gmail.com");
        userEntity.setPassword(CryptoUtils.encodePassword("sylwia"));
        String token = CryptoUtils.generateUserToken(userEntity.getEmail());
        userEntity.setToken(token);
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        session.save(userEntity);
        transaction.commit();
        session.close();
        Session session1 = sessionFactory.openSession();
        List userWithTokenList = session1.createCriteria(UserEntity.class).add(Restrictions.eq("token", token)).list();
        Assert.assertNotNull(userWithTokenList);
        Assert.assertEquals(1, userWithTokenList.size());
        session1.beginTransaction();
        session1.delete(userWithTokenList.get(0));
        session1.getTransaction().commit();
        session1.close();
    }

    @AfterClass
    public static void clear() {
        sessionFactory.close();
    }
}
