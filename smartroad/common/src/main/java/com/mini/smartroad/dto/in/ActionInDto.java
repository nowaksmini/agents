package com.mini.smartroad.dto.in;

import com.mini.smartroad.common.ActionType;

import java.io.Serializable;

public class ActionInDto implements Serializable {

    /**
     * used for:
     * like : value = 1, actionType = LIKE, previousActionToken null
     * unlike : value = 0, actionType = LIKE, previousActionToken not null
     * relike : value = 1, actionType = LIKE, previousActionToken not null // old like's value = 0
     * confirm : value = 1, actionType = CONFIRM, previousActionToken null
     * unconfirm : value = 0, actionType = CONFIRM, previousActionToken not null
     */
    private String previousActionToken;
    private String stationToken;
    private String userToken;
    private boolean value;
    private ActionType actionType;

    public ActionInDto(String previousActionToken, String stationToken, String userToken, boolean value, ActionType actionType) {
        this.previousActionToken = previousActionToken;
        this.stationToken = stationToken;
        this.userToken = userToken;
        this.value = value;
        this.actionType = actionType;
    }

    public String getPreviousActionToken() {
        return previousActionToken;
    }

    public String getStationToken() {
        return stationToken;
    }

    public String getUserToken() {
        return userToken;
    }

    public boolean isValue() {
        return value;
    }

    public ActionType getActionType() {
        return actionType;
    }

    @Override
    public String toString() {
        return "ActionInDto{" +
                "previousActionToken='" + previousActionToken + '\'' +
                ", stationToken='" + stationToken + '\'' +
                ", userToken='" + userToken + '\'' +
                ", value=" + value +
                ", actionType=" + actionType +
                '}';
    }
}
