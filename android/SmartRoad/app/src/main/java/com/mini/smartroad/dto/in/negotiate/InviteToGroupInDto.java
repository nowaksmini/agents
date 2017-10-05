package com.mini.smartroad.dto.in.negotiate;

import com.mini.smartroad.dto.in.BaseInDto;

import java.io.Serializable;

public class InviteToGroupInDto extends BaseInDto implements Serializable {
    private String stationToken;
    private String invitedUserAgentName;

    public InviteToGroupInDto(String token, String stationToken, String invitedUserAgentName) {
        super(token);
        this.stationToken = stationToken;
        this.invitedUserAgentName = invitedUserAgentName;
    }
}
