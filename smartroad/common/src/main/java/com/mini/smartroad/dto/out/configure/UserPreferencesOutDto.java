package com.mini.smartroad.dto.out.configure;

import com.mini.smartroad.dto.out.StatusOutDto;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

@Data
@AllArgsConstructor
public class UserPreferencesOutDto implements Serializable {
    private Integer minimalMinutesLeft = 30;
    private Integer startSearchingMinutesLeft = 60;
    private Boolean acceptAlways = false;
    private String avoidedStationNames = "";
    private String preferredStationNames = "";
    private StatusOutDto statusOutDto;

    public UserPreferencesOutDto(StatusOutDto statusOutDto) {
        this.statusOutDto = statusOutDto;
    }
}
