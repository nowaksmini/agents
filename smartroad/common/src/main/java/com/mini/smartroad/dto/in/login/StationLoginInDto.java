package com.mini.smartroad.dto.in.login;

import lombok.Data;

import java.io.Serializable;

@Data
public class StationLoginInDto implements Serializable {
    private final String userName;
    private final String secretCode;
}
