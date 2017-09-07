package com.mini.smartroad.dto.in.negotiate;

import com.mini.smartroad.dto.in.BaseInDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
public class MakeGroupInDto extends BaseInDto implements Serializable {
    private String stationToken;

    public MakeGroupInDto(String token, String stationToken) {
        super(token);
        this.stationToken = stationToken;
    }
}
