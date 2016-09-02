package com.mini.smartroad.dto.in;

import com.mini.smartroad.dto.AddressDto;

import java.io.Serializable;

public class StationRegisterInDto implements Serializable {

    private String name;
    private String fullName;
    private String email;
    private String logo;
    private String phone;
    private double longitude;
    private double latitude;
    private AddressDto addressDto;

    public StationRegisterInDto(String name, String fullName, String email, String logo,
                                String phone, double longitude, double latitude, AddressDto addressDto) {
        this.name = name;
        this.fullName = fullName;
        this.email = email;
        this.logo = logo;
        this.phone = phone;
        this.longitude = longitude;
        this.latitude = latitude;
        this.addressDto = addressDto;
    }

    public String getName() {
        return name;
    }

    public String getFullName() {
        return fullName;
    }

    public String getEmail() {
        return email;
    }

    public String getLogo() {
        return logo;
    }

    public String getPhone() {
        return phone;
    }

    public double getLongitude() {
        return longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public AddressDto getAddressDto() {
        return addressDto;
    }

    @Override
    public String toString() {
        return "StationRegisterInDto{" +
                "name='" + name + '\'' +
                ", fullName='" + fullName + '\'' +
                ", email='" + email + '\'' +
                ", logo='" + logo + '\'' +
                ", phone='" + phone + '\'' +
                ", longitude='" + longitude + '\'' +
                ", latitude='" + latitude + '\'' +
                ", addressDto=" + addressDto +
                '}';
    }
}
