package com.mini.smartroad.dto.out.negotiate;

import com.mini.smartroad.dto.out.StatusOutDto;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
@AllArgsConstructor
public class FindUsersOutDto implements Serializable {
    private List<String> availableUsers;
    private StatusOutDto statusOutDto;

    public FindUsersOutDto(StatusOutDto statusOutDto) {
        this.statusOutDto = statusOutDto;
    }
}
