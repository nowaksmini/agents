package com.mini.smartroad.dto.in.login;

import java.io.Serializable;

public class UserLoginInDto implements Serializable {
    private final String login;
    private final String password;

    public UserLoginInDto(String login, String password) {
        this.login = login;
        this.password = password;
    }
}
