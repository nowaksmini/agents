package com.mini.smartroad.dto.in.negotiate;

import com.mini.smartroad.dto.in.BaseInDto;

import java.io.Serializable;

public class FindUsersInDto extends BaseInDto implements Serializable {
    private String stationToken;

    public FindUsersInDto(String token, String stationToken) {
        super(token);
        this.stationToken = stationToken;
    }
}
