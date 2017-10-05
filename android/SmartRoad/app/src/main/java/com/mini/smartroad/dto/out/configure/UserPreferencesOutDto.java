package com.mini.smartroad.dto.out.configure;

import com.mini.smartroad.dto.out.StatusOutDto;

import java.io.Serializable;

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

    public UserPreferencesOutDto(Integer minimalMinutesLeft, Integer startSearchingMinutesLeft,
                                 Boolean acceptAlways, String avoidedStationNames,
                                 String preferredStationNames, StatusOutDto statusOutDto) {
        this.minimalMinutesLeft = minimalMinutesLeft;
        this.startSearchingMinutesLeft = startSearchingMinutesLeft;
        this.acceptAlways = acceptAlways;
        this.avoidedStationNames = avoidedStationNames;
        this.preferredStationNames = preferredStationNames;
        this.statusOutDto = statusOutDto;
    }

    public Integer getMinimalMinutesLeft() {
        return minimalMinutesLeft;
    }

    public Integer getStartSearchingMinutesLeft() {
        return startSearchingMinutesLeft;
    }

    public Boolean getAcceptAlways() {
        return acceptAlways;
    }

    public String getAvoidedStationNames() {
        return avoidedStationNames;
    }

    public String getPreferredStationNames() {
        return preferredStationNames;
    }

    public StatusOutDto getStatusOutDto() {
        return statusOutDto;
    }
}
