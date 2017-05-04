package com.mini.smartroad.dto.in;

import com.mini.smartroad.common.ActionType;

import java.io.Serializable;

public class ActionInDto extends BaseInDto implements Serializable {

    private String stationToken;
    private Boolean value;
    private ActionType actionType;

    public ActionInDto(String stationToken, String userToken, Boolean value, ActionType actionType) {
        this.stationToken = stationToken;
        this.userToken = userToken;
        this.value = value;
        this.actionType = actionType;
    }

    public String getStationToken() {
        return stationToken;
    }

    public Boolean getValue() {
        return value;
    }

    public ActionType getActionType() {
        return actionType;
    }

    @Override
    public String toString() {
        return "ActionInDto{" +
                "stationToken='" + stationToken + '\'' +
                ", userToken='" + userToken + '\'' +
                ", value=" + value +
                ", actionType=" + actionType +
                '}';
    }
}
