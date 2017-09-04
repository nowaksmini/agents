package com.mini.smartroad.dto.out.configure;

import com.mini.smartroad.dto.out.StatusOutDto;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

@Data
@AllArgsConstructor
public class StationNegotiationStrategyOutDto implements Serializable {
    private Integer groupSize0 = 2;
    private Integer groupSize1 = 5;
    private Integer groupSize2 = 7;
    private Integer groupSize3 = 10;
    private Integer groupSize4 = 15;

    private Integer pointsGroupSize0 = 10;
    private Integer pointsGroupSize1 = 60;
    private Integer pointsGroupSize2 = 90;
    private Integer pointsGroupSize3 = 150;
    private Integer pointsGroupSize4 = 300;

    private StatusOutDto statusOutDto;

    public StationNegotiationStrategyOutDto(StatusOutDto statusOutDto) {
        this.statusOutDto = statusOutDto;
    }
}
