package com.mini.smartroad.events;

public class UserEvent {
    private String userToken;

    public UserEvent(String userToken) {
        this.userToken = userToken;
    }

    public String getUserToken() {
        return userToken;
    }
}
