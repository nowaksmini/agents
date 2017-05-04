package com.mini.smartroad.dto.in;

import java.io.Serializable;

public class FindStationsInDto extends BaseInDto implements Serializable {
    private double latitude;
    private double longitude;
    private Long distance; // kilometers

    public FindStationsInDto(String userToken, double latitude, double longitude, Long distance) {
        this.userToken = userToken;
        this.latitude = latitude;
        this.longitude = longitude;
        this.distance = distance;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public Long getDistance() {
        return distance;
    }

    @Override
    public String toString() {
        return "FindStationsInDto{" +
                "latitude=" + latitude +
                ", longitude=" + longitude +
                ", distance=" + distance +
                '}';
    }
}
