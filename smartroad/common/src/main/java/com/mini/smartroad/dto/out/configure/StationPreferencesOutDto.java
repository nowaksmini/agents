package com.mini.smartroad.dto.out.configure;

import com.mini.smartroad.dto.out.StatusOutDto;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

@Data
@AllArgsConstructor
public class StationPreferencesOutDto implements Serializable {
    private String name;
    private String email;
    private String phone;
    private String logo;
    private String address;
    private StatusOutDto statusOutDto;

    public StationPreferencesOutDto(StatusOutDto statusOutDto) {
        this.statusOutDto = statusOutDto;
    }
}