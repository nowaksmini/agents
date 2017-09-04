package com.mini.smartroad.dto.in.register;

import com.mini.smartroad.dto.AddressDto;
import lombok.Data;

import java.io.Serializable;

@Data
public class StationRegisterInDto implements Serializable {

    private final String userName;
    private final String name;
    private final String email;
    private final String logo;
    private final String phone;
    private final double longitude;
    private final double latitude;
    private final AddressDto addressDto;
}
