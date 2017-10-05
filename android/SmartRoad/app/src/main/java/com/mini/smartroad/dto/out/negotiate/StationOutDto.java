package com.mini.smartroad.dto.out.negotiate;

import com.mini.smartroad.common.ActionType;
import com.mini.smartroad.dto.AddressDto;

import java.io.Serializable;

public class StationOutDto implements Serializable {

    private String name;
    private String email;
    private String logo;
    private String token;
    private String phone;
    private Double longitude;
    private Double latitude;
    private Integer counter;
    private Integer points;
    private Integer minCarsAmount;
    /**
     * Shows what action driver actually made
     */
    private ActionType actionType;
    private AddressDto addressDto;

    public StationOutDto(String name, String email, String logo, String token, String phone,
                         Double longitude, Double latitude, Integer counter, Integer points,
                         Integer minCarsAmount, ActionType actionType, AddressDto addressDto) {
        this.name = name;
        this.email = email;
        this.logo = logo;
        this.token = token;
        this.phone = phone;
        this.longitude = longitude;
        this.latitude = latitude;
        this.counter = counter;
        this.points = points;
        this.minCarsAmount = minCarsAmount;
        this.actionType = actionType;
        this.addressDto = addressDto;
    }

    public String getName() {
        return name;
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

    public Integer getCounter() {
        return counter;
    }

    public Integer getPoints() {
        return points;
    }

    public Integer getMinCarsAmount() {
        return minCarsAmount;
    }

    public ActionType getActionType() {
        return actionType;
    }

    public AddressDto getAddressDto() {
        return addressDto;
    }
}
