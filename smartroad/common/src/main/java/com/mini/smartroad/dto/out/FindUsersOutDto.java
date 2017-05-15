package com.mini.smartroad.dto.out;

import java.io.Serializable;
import java.util.List;

public class FindUsersOutDto implements Serializable {
    private List<String> driversAgentsNames;
    private StatusOutDto statusOut;

    public FindUsersOutDto(List<String> driversAgentsNames, StatusOutDto statusOut) {
        this.driversAgentsNames = driversAgentsNames;
        this.statusOut = statusOut;
    }

    public List<String> getDriversAgentsNames() {
        return driversAgentsNames;
    }

    public StatusOutDto getStatusOut() {
        return statusOut;
    }

    @Override
    public String toString() {
        return "FindUsersOutDto{" +
                "driversAgentsNames=" + driversAgentsNames +
                ", statusOut=" + statusOut +
                '}';
    }
}
