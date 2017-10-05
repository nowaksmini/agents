package com.mini.smartroad.dto.in.negotiate;

import com.mini.smartroad.dto.in.BaseInDto;

import java.io.Serializable;

public class AskForJoiningGroupInDto extends BaseInDto implements Serializable {
    private String stationToken;

    public AskForJoiningGroupInDto(String token, String stationToken) {
        super(token);
        this.stationToken = stationToken;
    }
}
