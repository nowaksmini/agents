package com.mini.smartroad.dto;


import java.io.Serializable;

public class StatusDto implements Serializable {
    private StatusType statusType;
    private String message;

    public StatusDto() {
        statusType = StatusType.OK;
    }

    public StatusDto(StatusType statusType, String message) {
        this.statusType = statusType;
        this.message = message;
    }

    public StatusType getStatusType() {
        return statusType;
    }

    public String getMessage() {
        return message;
    }

    @Override
    public String toString() {
        return "StatusDto{" +
                "statusType=" + statusType +
                ", message='" + message + '\'' +
                '}';
    }
}
