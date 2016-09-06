package com.mini.smartroad.events;

import com.mini.smartroad.dto.out.FindStationsOutDto;

public class FoundStationsEvent {
    private FindStationsOutDto findStationsOutDto;

    public FoundStationsEvent(FindStationsOutDto findStationsOutDto) {
        this.findStationsOutDto = findStationsOutDto;
    }

    public FindStationsOutDto getFindStationsOutDto() {
        return findStationsOutDto;
    }
}
