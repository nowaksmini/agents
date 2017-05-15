package com.mini.smartroad.dto.in;

import java.io.Serializable;

public class FindDriversInDto extends BaseInDto implements Serializable {
    private String stationToken;

    public FindDriversInDto(String userToken, String stationToken) {
        this.userToken = userToken;
        this.stationToken = stationToken;
    }

    public String getStationToken() {
        return stationToken;
    }

    @Override
    public String toString() {
        return "FindDriversInDto{" +
                "stationToken='" + stationToken + '\'' +
                '}';
    }
}
