package com.mini.smartroad;

import com.mini.smartroad.model.StationDetailsEntity;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

public class StationTest {

    private static SessionFactory sessionFactory;

    @Before
    public void configure() {
        sessionFactory = HibernateUtils.getSessionFactory();
    }

    @Test
    public void testCreateStation() {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();

        StationDetailsEntity stationDetailsEntity = TestBuilder.buildStation();

        String stationToken = stationDetailsEntity.getToken();
        session.save(stationDetailsEntity);
        transaction.commit();
        session.close();

        Session session1 = sessionFactory.openSession();
        List foundStations = session1.createCriteria(StationDetailsEntity.class).add(Restrictions.eq("token", stationToken)).list();
        Assert.assertNotNull(foundStations);
        Assert.assertEquals(1, foundStations.size());
        session1.beginTransaction();
        session1.delete(foundStations.get(0));
        session1.getTransaction().commit();
        session1.close();
    }

//    @After
//    public void clear() {
//        sessionFactory.close();
//    }

}
