package com.mini.smartroad.dto.in.configure;

import com.mini.smartroad.dto.in.BaseInDto;

public class StationNegotiationStrategyInDto extends BaseInDto {
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

    public StationNegotiationStrategyInDto(String token, Integer groupSize0, Integer groupSize1,
                                           Integer groupSize2, Integer groupSize3, Integer groupSize4,
                                           Integer pointsGroupSize0, Integer pointsGroupSize1,
                                           Integer pointsGroupSize2, Integer pointsGroupSize3,
                                           Integer pointsGroupSize4) {
        super(token);
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
    }
}
