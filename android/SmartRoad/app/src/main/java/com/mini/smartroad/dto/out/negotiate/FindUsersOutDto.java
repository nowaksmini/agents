package com.mini.smartroad.dto.out.negotiate;

import com.mini.smartroad.dto.out.StatusOutDto;

import java.io.Serializable;
import java.util.List;

public class FindUsersOutDto implements Serializable {
    private List<String> availableUsers;
    private StatusOutDto statusOutDto;

    public FindUsersOutDto(StatusOutDto statusOutDto) {
        this.statusOutDto = statusOutDto;
    }

    public FindUsersOutDto(List<String> availableUsers, StatusOutDto statusOutDto) {
        this.availableUsers = availableUsers;
        this.statusOutDto = statusOutDto;
    }

    public List<String> getAvailableUsers() {
        return availableUsers;
    }

    public StatusOutDto getStatusOutDto() {
        return statusOutDto;
    }
}
