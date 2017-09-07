package com.mini.smartroad.dto.out.negotiate;

import com.mini.smartroad.common.ActionType;
import com.mini.smartroad.dto.AddressDto;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

@Data
@AllArgsConstructor
public class StationOutDto implements Serializable {

    private String name;
    private String email;
    private String logo;
    private String token;
    private String phone;
    private Double longitude;
    private Double latitude;
    private Integer counter;
    private Integer points;
    private Integer minCarsAmount;
    /**
     * Shows what action driver actually made
     */
    private ActionType actionType;
    private AddressDto addressDto;
}
