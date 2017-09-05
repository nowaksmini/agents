package com.mini.smartroad.dto.in.negotiate;

import com.mini.smartroad.dto.in.BaseInDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
public class FindStationsInDto extends BaseInDto implements Serializable {
    private Long distance; // kilometers

    public FindStationsInDto(String token, Long distance) {
        super(token);
        this.distance = distance;
    }
}