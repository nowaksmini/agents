package com.mini.smartroad.dto.out.login_register;

import com.mini.smartroad.dto.out.StatusOutDto;

import java.io.Serializable;

public class LoginRegisterStationOutDto implements Serializable {
    private String token;
    private String secretCode;
    private StatusOutDto statusOutDto;

    public LoginRegisterStationOutDto(StatusOutDto statusOutDto) {
        this.statusOutDto = statusOutDto;
    }

    public LoginRegisterStationOutDto(String token, String secretCode, StatusOutDto statusOutDto) {
        this.token = token;
        this.secretCode = secretCode;
        this.statusOutDto = statusOutDto;
    }

    public String getToken() {
        return token;
    }

    public String getSecretCode() {
        return secretCode;
    }

    public StatusOutDto getStatusOutDto() {
        return statusOutDto;
    }
}
