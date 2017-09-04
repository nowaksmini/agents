package com.mini.smartroad.dto.out.login_register;

import com.mini.smartroad.dto.out.StatusOutDto;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

@Data
@AllArgsConstructor
public class LoginRegisterUserOutDto implements Serializable {
    private String token;
    private String firstName;
    private String lastName;
    private String email;
    private StatusOutDto statusOutDto;

    public LoginRegisterUserOutDto(StatusOutDto statusOutDto) {
        this.statusOutDto = statusOutDto;
    }
}
