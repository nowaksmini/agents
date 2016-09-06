package com.mini.smartroad.events;

public class FailureEvent {
    private String message;

    public FailureEvent(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
