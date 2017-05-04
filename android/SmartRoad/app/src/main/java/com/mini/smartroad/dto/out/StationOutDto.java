package com.mini.smartroad.dto.out;

import com.mini.smartroad.common.ActionType;
import com.mini.smartroad.dto.AddressDto;

import java.io.Serializable;

public class StationOutDto implements Serializable {

    private AddressDto addressDto;
    private String name;
    private String fullName;
    private String email;
    private String logo;
    private String token;
    private String phone;
    private Double longitude;
    private Double latitude;
    private Integer likes;
    private Integer confirms;
    /**
     * Shows what action user actually made LIKE, CONFIRM
     */
    private ActionType actionType;

    public StationOutDto(AddressDto addressDto, String name, String fullName, String email, String logo, String token,
                         String phone, Double longitude, Double latitude, Integer likes,
                         Integer confirms, ActionType actionType) {
        this.addressDto = addressDto;
        this.name = name;
        this.fullName = fullName;
        this.email = email;
        this.logo = logo;
        this.token = token;
        this.phone = phone;
        this.longitude = longitude;
        this.latitude = latitude;
        this.likes = likes;
        this.confirms = confirms;
        this.actionType = actionType;
    }

    public AddressDto getAddressDto() {
        return addressDto;
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

    public String getToken() {
        return token;
    }

    public String getPhone() {
        return phone;
    }

    public Double getLongitude() {
        return longitude;
    }

    public Double getLatitude() {
        return latitude;
    }

    public Integer getLikes() {
        return likes;
    }

    public Integer getConfirms() {
        return confirms;
    }

    public ActionType getActionType() {
        return actionType;
    }

    @Override
    public String toString() {
        return "StationOutDto{" +
                "addressDto=" + addressDto +
                ", name='" + name + '\'' +
                ", fullName='" + fullName + '\'' +
                ", email='" + email + '\'' +
                ", logo='" + logo + '\'' +
                ", token='" + token + '\'' +
                ", phone='" + phone + '\'' +
                ", longitude=" + longitude +
                ", latitude=" + latitude +
                ", likes=" + likes +
                ", confirms=" + confirms +
                ", actionType=" + actionType +
                '}';
    }
}
