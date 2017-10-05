package com.mini.smartroad.dto.out.login_register;

import com.mini.smartroad.dto.out.StatusOutDto;

import java.io.Serializable;

public class LoginRegisterUserOutDto implements Serializable {
    private String token;
    private String firstName;
    private String lastName;
    private String email;
    private StatusOutDto statusOutDto;

    public LoginRegisterUserOutDto(StatusOutDto statusOutDto) {
        this.statusOutDto = statusOutDto;
    }

    public LoginRegisterUserOutDto(String token, String firstName, String lastName, String email, StatusOutDto statusOutDto) {
        this.token = token;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.statusOutDto = statusOutDto;
    }

    public String getToken() {
        return token;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmail() {
        return email;
    }

    public StatusOutDto getStatusOutDto() {
        return statusOutDto;
    }
}
