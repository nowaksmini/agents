package com.mini.smartroad.dto.out;


import java.io.Serializable;

public class StatusOutDto implements Serializable {
    private StatusType statusType;
    private String message;

    public StatusOutDto() {
        statusType = StatusType.OK;
    }

    public StatusOutDto(StatusType statusType, String message) {
        this.statusType = statusType;
        this.message = message;
    }

    public StatusType getStatusType() {
        return statusType;
    }

    public String getMessage() {
        return message;
    }

    public void setStatusType(StatusType statusType) {
        this.statusType = statusType;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "StatusDto{" +
                "statusType=" + statusType +
                ", message='" + message + '\'' +
                '}';
    }
}
