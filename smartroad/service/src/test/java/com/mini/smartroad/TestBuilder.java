package com.mini.smartroad;

import com.mini.smartroad.common.ActionType;
import com.mini.smartroad.common.CryptoUtils;
import com.mini.smartroad.common.Utils;
import com.mini.smartroad.dto.AddressDto;
import com.mini.smartroad.dto.in.StationRegisterInDto;
import com.mini.smartroad.model.*;

import java.util.Calendar;
import java.util.Date;

public class TestBuilder {

    public static UserEntity buildUser() {
        UserEntity userEntity = new UserEntity();
        userEntity.setLastName("test_last_name");
        userEntity.setFirstName("test_first_name");
        userEntity.setEmail("test@gmail.com");
        userEntity.setPassword(CryptoUtils.encodePassword("password"));
        String token = CryptoUtils.generateUserToken(userEntity.getEmail());
        userEntity.setToken(token);
        return userEntity;
    }

    public static StationEntity buildStation() {
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
        return stationEntity;
    }

    public static ActionEntity buildActionLike(UserEntity userEntity, StationEntity stationEntity) {
        ActionEntity actionEntity = new ActionEntity();
        actionEntity.setUser(userEntity);
        actionEntity.setStation(stationEntity);
        actionEntity.setDateFrom(new Date());
        Calendar to = Calendar.getInstance();
        to.add(Calendar.HOUR, (int) Utils.LIKE_TIME_DURATION);
        actionEntity.setDateTo(to.getTime());
        actionEntity.setActionType(ActionType.LIKE);
        actionEntity.setValue(Boolean.TRUE);
        actionEntity.setToken(CryptoUtils.generateActionToken(actionEntity.getActionType(), userEntity.getToken(),
                stationEntity.getToken()));
        return actionEntity;
    }

    public static CouponEntity buildCoupon(ActionEntity actionEntity) {
        CouponEntity couponEntity = new CouponEntity();
        couponEntity.setValue(Boolean.TRUE);
        couponEntity.setAction(actionEntity);
        couponEntity.setValidFrom(new Date());
        Calendar to = Calendar.getInstance();
        to.add(Calendar.HOUR, (int) Utils.COUPON_TIME_DURATION);
        couponEntity.setValidTo(to.getTime());
        couponEntity.setToken(CryptoUtils.generateCouponToken(actionEntity.getToken()));
        couponEntity.setDiscountCode(CryptoUtils.generateDiscountCode(couponEntity.getToken()));
        return couponEntity;
    }
}
