package com.mini.smartroad.dto.in.negotiate;

import com.mini.smartroad.dto.in.BaseInDto;

import java.io.Serializable;

public class FindStationsInDto extends BaseInDto implements Serializable {
    private Long distance; // kilometers

    public FindStationsInDto(String token, Long distance) {
        super(token);
        this.distance = distance;
    }
}
