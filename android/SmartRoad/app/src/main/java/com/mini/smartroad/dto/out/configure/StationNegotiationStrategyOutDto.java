package com.mini.smartroad.dto.out.configure;

import com.mini.smartroad.dto.out.StatusOutDto;

import java.io.Serializable;

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

    public StationNegotiationStrategyOutDto(Integer groupSize0, Integer groupSize1, Integer groupSize2,
                                            Integer groupSize3, Integer groupSize4, Integer pointsGroupSize0,
                                            Integer pointsGroupSize1, Integer pointsGroupSize2,
                                            Integer pointsGroupSize3, Integer pointsGroupSize4,
                                            StatusOutDto statusOutDto) {
        this.groupSize0 = groupSize0;
        this.groupSize1 = groupSize1;
        this.groupSize2 = groupSize2;
        this.groupSize3 = groupSize3;
        this.groupSize4 = groupSize4;
        this.pointsGroupSize0 = pointsGroupSize0;
        this.pointsGroupSize1 = pointsGroupSize1;
        this.pointsGroupSize2 = pointsGroupSize2;
        this.pointsGroupSize3 = pointsGroupSize3;
        this.pointsGroupSize4 = pointsGroupSize4;
        this.statusOutDto = statusOutDto;
    }

    public Integer getGroupSize0() {
        return groupSize0;
    }

    public Integer getGroupSize1() {
        return groupSize1;
    }

    public Integer getGroupSize2() {
        return groupSize2;
    }

    public Integer getGroupSize3() {
        return groupSize3;
    }

    public Integer getGroupSize4() {
        return groupSize4;
    }

    public Integer getPointsGroupSize0() {
        return pointsGroupSize0;
    }

    public Integer getPointsGroupSize1() {
        return pointsGroupSize1;
    }

    public Integer getPointsGroupSize2() {
        return pointsGroupSize2;
    }

    public Integer getPointsGroupSize3() {
        return pointsGroupSize3;
    }

    public Integer getPointsGroupSize4() {
        return pointsGroupSize4;
    }

    public StatusOutDto getStatusOutDto() {
        return statusOutDto;
    }
}
