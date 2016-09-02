package com.mini.smartroad.dto.in;

import java.io.Serializable;

public class FindStationsInDto implements Serializable {
    private double latitude;
    private double longitude;
    private long distance; // meters

    public FindStationsInDto(double latitude, double longitude, long distance) {
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

    public long getDistance() {
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
