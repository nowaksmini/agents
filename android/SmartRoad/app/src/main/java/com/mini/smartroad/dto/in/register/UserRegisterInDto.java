package com.mini.smartroad.dto.in.register;


import java.io.Serializable;

public class UserRegisterInDto implements Serializable {

    private final String email;
    private final String firstName;
    private final String lastName;
    private final String password;

    public UserRegisterInDto(String email, String firstName, String lastName, String password) {
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.password = password;
    }
}
