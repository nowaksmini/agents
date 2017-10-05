package com.mini.smartroad.dto.out.configure;

import com.mini.smartroad.dto.out.StatusOutDto;
import java.io.Serializable;

public class StationPreferencesOutDto implements Serializable {
    private String name;
    private String email;
    private String phone;
    private String logo;
    private String address;
    private StatusOutDto statusOutDto;

    public StationPreferencesOutDto(StatusOutDto statusOutDto) {
        this.statusOutDto = statusOutDto;
    }

    public StationPreferencesOutDto(String name, String email, String phone, String logo,
                                    String address, StatusOutDto statusOutDto) {
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.logo = logo;
        this.address = address;
        this.statusOutDto = statusOutDto;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getPhone() {
        return phone;
    }

    public String getLogo() {
        return logo;
    }

    public String getAddress() {
        return address;
    }

    public StatusOutDto getStatusOutDto() {
        return statusOutDto;
    }
}