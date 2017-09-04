package com.mini.smartroad.dto.in.login;

import lombok.Data;

import java.io.Serializable;

@Data
public class UserLoginInDto implements Serializable {

    private final String login;
    private final String password;
}
