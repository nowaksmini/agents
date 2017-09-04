package com.mini.smartroad.dto.in;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
public class FindDriversInDto extends BaseInDto implements Serializable {
    private String stationToken;
}
