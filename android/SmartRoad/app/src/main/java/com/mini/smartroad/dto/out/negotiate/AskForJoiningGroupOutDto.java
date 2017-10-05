package com.mini.smartroad.dto.out.negotiate;

import com.mini.smartroad.common.ActionType;
import com.mini.smartroad.dto.out.StatusOutDto;

import java.io.Serializable;

public class AskForJoiningGroupOutDto implements Serializable {
    private ActionType actionType;
    private StatusOutDto stationOutDto;

    public AskForJoiningGroupOutDto(StatusOutDto stationOutDto) {
        this.stationOutDto = stationOutDto;
    }

    public AskForJoiningGroupOutDto(ActionType actionType, StatusOutDto stationOutDto) {
        this.actionType = actionType;
        this.stationOutDto = stationOutDto;
    }

    public ActionType getActionType() {
        return actionType;
    }

    public StatusOutDto getStationOutDto() {
        return stationOutDto;
    }

    public void setActionType(ActionType actionType) {
        this.actionType = actionType;
    }

    public void setStationOutDto(StatusOutDto stationOutDto) {
        this.stationOutDto = stationOutDto;
    }
}
