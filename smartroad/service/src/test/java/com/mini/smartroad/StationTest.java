package com.mini.smartroad;

import com.mini.smartroad.common.CryptoUtils;
import com.mini.smartroad.dto.AddressDto;
import com.mini.smartroad.dto.in.StationRegisterInDto;
import com.mini.smartroad.model.AddressEntity;
import com.mini.smartroad.model.StationEntity;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.List;

public class StationTest {

    private static SessionFactory sessionFactory;

    @BeforeClass
    public static void configure() {
        sessionFactory = HibernateUtils.getSessionFactory();
    }

    @Test
    public void testCreateStation() {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();

        AddressEntity addressEntity = new AddressEntity();
        AddressDto addressDto = new AddressDto("polska", "mazowieckie", "warszawa",
                "Królewska", "10a", "/57", "02-237");
        addressEntity.setCountry(addressDto.getCountry());
        addressEntity.setCity(addressDto.getCity());
        addressEntity.setProvince(addressDto.getDistinct());
        addressEntity.setPostalCode(addressDto.getPostalCode());
        addressEntity.setNumber(addressDto.getNumber());
        addressEntity.setStreet(addressDto.getStreet());
        addressEntity.setExtraNumber(addressDto.getExtraNumber());

        StationRegisterInDto stationRegisterInDto = new StationRegisterInDto("Shell",
                "Shell stacja paliw", "shell@gmail.com", "", "123 456 789",
                50, 50, addressDto);
        StationEntity stationEntity = new StationEntity();
        stationEntity.setEmail(stationRegisterInDto.getEmail());
        stationEntity.setPhone(stationRegisterInDto.getPhone());
        stationEntity.setLatitude(stationRegisterInDto.getLatitude());
        stationEntity.setLongitude(stationRegisterInDto.getLongitude());
        stationEntity.setLogo(stationRegisterInDto.getLogo());
        stationEntity.setName(stationRegisterInDto.getName());
        stationEntity.setFullName(stationRegisterInDto.getFullName());
        stationEntity.setToken(CryptoUtils.generateStationToken(stationRegisterInDto.getName(),
                stationRegisterInDto.getLongitude(), stationEntity.getLatitude()));
        stationEntity.setAddress(addressEntity);
        addressEntity.setStation(stationEntity);

        String stationToken = stationEntity.getToken();
        session.save(stationEntity);
        transaction.commit();
        session.close();

        Session session1 = sessionFactory.openSession();
        List userWithTokenList = session1.createCriteria(StationEntity.class).add(Restrictions.eq("token", stationToken)).list();
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
