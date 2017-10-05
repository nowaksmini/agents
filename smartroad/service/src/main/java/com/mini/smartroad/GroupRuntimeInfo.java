package com.mini.smartroad;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class GroupRuntimeInfo {
    private String stationToken;
    private LocalDateTime from;
    private LocalDateTime to;
    private int currentNumberOfCars;
    private int currentReward;
}
