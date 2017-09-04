package com.mini.smartroad.dto.in.configure;

import com.mini.smartroad.dto.in.BaseInDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
public class UserPreferencesInDto extends BaseInDto {
    private Integer minimalMinutesLeft = 30;
    private Integer startSearchingMinutesLeft = 60;
    private Boolean acceptAlways = false;
    private String avoidedStationNames = "";
    private String preferredStationNames = "";

    public UserPreferencesInDto(String token, Integer minimalMinutesLeft,
                                Integer startSearchingMinutesLeft, Boolean acceptAlways,
                                String avoidedStationNames, String preferredStationNames) {
        super(token);
        this.minimalMinutesLeft = minimalMinutesLeft;
        this.startSearchingMinutesLeft = startSearchingMinutesLeft;
        this.acceptAlways = acceptAlways;
        this.avoidedStationNames = avoidedStationNames;
        this.preferredStationNames = preferredStationNames;
    }
}
