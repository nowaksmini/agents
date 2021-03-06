package com.mini.smartroad;

import com.mini.smartroad.model.ActionEntity;
import com.mini.smartroad.model.CouponEntity;
import com.mini.smartroad.model.StationDetailsEntity;
import com.mini.smartroad.model.UserEntity;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

public class CouponTest {

    private static SessionFactory sessionFactory;

    @Before
    public void configure() {
        sessionFactory = HibernateUtils.getSessionFactory();
    }

    @Test
    public void testCreateCoupon() {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        UserEntity userEntity = TestBuilder.buildUser();
        String userToken = userEntity.getToken();
        StationDetailsEntity stationDetailsEntity = TestBuilder.buildStation();
        String stationToken = stationDetailsEntity.getToken();
        ActionEntity actionEntity = TestBuilder.buildActionLike(userEntity, stationDetailsEntity);
        String actionToken = actionEntity.getToken();
        CouponEntity couponEntity = TestBuilder.buildCoupon(actionEntity);
        String couponToken = couponEntity.getToken();
        session.persist(couponEntity);
        session.save(couponEntity);
        transaction.commit();
        session.close();

        Session session1 = sessionFactory.openSession();

        List foundCoupons = session1.createCriteria(CouponEntity.class).add(Restrictions.eq("token", couponToken)).list();
        Assert.assertNotNull(foundCoupons);
        Assert.assertEquals(1, foundCoupons.size());

        List foundActions = session1.createCriteria(ActionEntity.class).add(Restrictions.eq("token", actionToken)).list();
        Assert.assertNotNull(foundActions);
        Assert.assertEquals(1, foundActions.size());

        List foundStations = session1.createCriteria(StationDetailsEntity.class).add(Restrictions.eq("token", stationToken)).list();
        Assert.assertNotNull(foundStations);
        Assert.assertEquals(1, foundStations.size());

        List foundUsers = session1.createCriteria(UserEntity.class).add(Restrictions.eq("token", userToken)).list();
        Assert.assertNotNull(foundUsers);
        Assert.assertEquals(1, foundUsers.size());

        session1.beginTransaction();
        session1.delete(foundCoupons.get(0));
        session1.delete(foundActions.get(0));
        session1.delete(foundStations.get(0));
        session1.delete(foundUsers.get(0));

        session1.getTransaction().commit();
        session1.close();
    }

//    @After
//    public void clear() {
//        sessionFactory.close();
//    }
}
