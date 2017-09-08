package com.mini.smartroad.dto.out.negotiate;

import com.mini.smartroad.common.ActionType;
import com.mini.smartroad.dto.out.StatusOutDto;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

@Data
@AllArgsConstructor
public class AskForJoiningGroupOutDto implements Serializable {
    private ActionType actionType;
    private StatusOutDto stationOutDto;

    public AskForJoiningGroupOutDto(StatusOutDto stationOutDto) {
        this.stationOutDto = stationOutDto;
    }
}
