package com.mini.smartroad.dto.in;

import java.io.Serializable;

public class BaseInDto implements Serializable {
    protected String userToken;

    public BaseInDto() {
    }

    public BaseInDto(String userToken) {
        this.userToken = userToken;
    }

    public String getUserToken() {
        return userToken;
    }

    @Override
    public String toString() {
        return "BaseInDto{" +
                "userToken='" + userToken + '\'' +
                '}';
    }
}
