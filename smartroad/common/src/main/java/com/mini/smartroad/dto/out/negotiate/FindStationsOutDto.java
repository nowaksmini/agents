package com.mini.smartroad.dto.out.negotiate;

import com.mini.smartroad.dto.out.StatusOutDto;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
@AllArgsConstructor
public class FindStationsOutDto implements Serializable {
    private List<StationOutDto> stations;
    private StatusOutDto statusOutDto;

    public FindStationsOutDto(StatusOutDto statusOutDto) {
        this.statusOutDto = statusOutDto;
    }
}
