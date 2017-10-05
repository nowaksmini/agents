package com.mini.smartroad.dto.out.negotiate;

import com.mini.smartroad.dto.out.StatusOutDto;

import java.io.Serializable;
import java.util.List;

public class FindStationsOutDto implements Serializable {
    private List<StationOutDto> stations;
    private StatusOutDto statusOutDto;

    public FindStationsOutDto(StatusOutDto statusOutDto) {
        this.statusOutDto = statusOutDto;
    }

    public FindStationsOutDto(List<StationOutDto> stations, StatusOutDto statusOutDto) {
        this.stations = stations;
        this.statusOutDto = statusOutDto;
    }

    public List<StationOutDto> getStations() {
        return stations;
    }

    public StatusOutDto getStatusOutDto() {
        return statusOutDto;
    }
}
