package com.mini.smartroad.dto.out.login_register;

import com.mini.smartroad.dto.out.StatusOutDto;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

@Data
@AllArgsConstructor
public class LoginRegisterStationOutDto implements Serializable {
    private String token;
    private String secretCode;
    private StatusOutDto statusOutDto;

    public LoginRegisterStationOutDto(StatusOutDto statusOutDto) {
        this.statusOutDto = statusOutDto;
    }
}
