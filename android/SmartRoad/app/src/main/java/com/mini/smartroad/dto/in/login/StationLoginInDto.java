package com.mini.smartroad.dto.in.login;

import java.io.Serializable;

public class StationLoginInDto implements Serializable {
    private final String userName;
    private final String secretCode;

    public StationLoginInDto(String userName, String secretCode) {
        this.userName = userName;
        this.secretCode = secretCode;
    }
}
