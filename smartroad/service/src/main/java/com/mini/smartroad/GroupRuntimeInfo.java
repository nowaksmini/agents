package com.mini.smartroad;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@AllArgsConstructor
public class GroupRuntimeInfo {
    private String stationToken;
    private LocalDateTime from;
    private LocalDateTime to;
    private int currentNumberOfCars;
    private int currentReward;
}
