package com.mini.smartroad;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
public class GroupRuntimeInfo {
    private UUID groupId;
    private String stationToken;
    private LocalDateTime from;
    private LocalDateTime to;
    private int currentNumberOfCars;
    private int currentReward;
}
