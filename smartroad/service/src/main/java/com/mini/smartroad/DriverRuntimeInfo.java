package com.mini.smartroad;

import lombok.Data;

import java.util.UUID;

@Data
public class DriverRuntimeInfo {
    private String token;
    private double longitude;
    private double latitude;
    private boolean wantsToNegotiate;
    private UUID groupId;

    public DriverRuntimeInfo(String token, double longitude, double latitude, boolean wantsToNegotiate) {
        this.token = token;
        this.longitude = longitude;
        this.latitude = latitude;
        this.wantsToNegotiate = wantsToNegotiate;
    }
}
