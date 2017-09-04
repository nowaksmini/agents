package com.mini.smartroad.dto.in.register;

import lombok.Data;

import java.io.Serializable;

@Data
public class UserRegisterInDto implements Serializable {

    private final String email;
    private final String firstName;
    private final String lastName;
    private final String password;
}
