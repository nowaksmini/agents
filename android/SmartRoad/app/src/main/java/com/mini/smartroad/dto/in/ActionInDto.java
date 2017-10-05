package com.mini.smartroad.dto.in;

import com.mini.smartroad.common.ActionType;

import java.io.Serializable;

public class ActionInDto extends BaseInDto implements Serializable {
    private String stationToken;
    private ActionType actionType;

    public ActionInDto(String token, String stationToken, ActionType actionType) {
        super(token);
        this.stationToken = stationToken;
        this.actionType = actionType;
    }
}
