package com.mini.smartroad.dto.out;

import java.io.Serializable;
import java.util.List;

public class FindStationsOutDto implements Serializable {
    private List<StationOutDto> stations;
    private StatusOutDto statusOut;

    public FindStationsOutDto(List<StationOutDto> stations, StatusOutDto statusOutDto) {
        this.stations = stations;
        this.statusOut = statusOutDto;
    }

    public FindStationsOutDto(StatusOutDto statusOutDto) {
        this.statusOut = statusOutDto;
    }

    public List<StationOutDto> getStations() {
        return stations;
    }

    public StatusOutDto getStatusOut() {
        return statusOut;
    }

    public void setStations(List<StationOutDto> stations) {
        this.stations = stations;
    }

    public void setStatusOut(StatusOutDto statusOut) {
        this.statusOut = statusOut;
    }

    @Override
    public String toString() {
        return "FindStationsOutDto{" +
                "stations=" + stations +
                ", statusOutDto=" + statusOut +
                '}';
    }
}
