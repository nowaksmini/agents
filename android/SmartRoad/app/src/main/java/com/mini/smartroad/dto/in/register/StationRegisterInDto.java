package com.mini.smartroad.dto.in.register;

import com.mini.smartroad.dto.AddressDto;

import java.io.Serializable;

public class StationRegisterInDto implements Serializable {

    private final String userName;
    private final String name;
    private final String email;
    private final String logo;
    private final String phone;
    private final double longitude;
    private final double latitude;
    private final AddressDto addressDto;

    public StationRegisterInDto(String userName, String name, String email, String logo,
                                String phone, double longitude, double latitude, AddressDto addressDto) {
        this.userName = userName;
        this.name = name;
        this.email = email;
        this.logo = logo;
        this.phone = phone;
        this.longitude = longitude;
        this.latitude = latitude;
        this.addressDto = addressDto;
    }
}
