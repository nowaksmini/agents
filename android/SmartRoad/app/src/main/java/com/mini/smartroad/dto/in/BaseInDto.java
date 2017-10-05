package com.mini.smartroad.dto.in;

import java.io.Serializable;

public class BaseInDto implements Serializable {
    protected String token;

    public BaseInDto(String token) {
        this.token = token;
    }

    public String getToken() {
        return token;
    }
}
