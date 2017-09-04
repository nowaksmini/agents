package com.mini.smartroad.dto.in.track;

import com.mini.smartroad.dto.in.BaseInDto;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class UserTrackInDto extends BaseInDto {
    private double latitude;
    private double longitude;
    private boolean wantsToNegotiate;

    public UserTrackInDto(String token, double latitude, double longitude, boolean wantsToNegotiate) {
        super(token);
        this.latitude = latitude;
        this.longitude = longitude;
        this.wantsToNegotiate = wantsToNegotiate;
    }
}
