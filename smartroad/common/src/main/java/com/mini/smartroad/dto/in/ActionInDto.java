package com.mini.smartroad.dto.in;

import com.mini.smartroad.common.ActionType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
public class ActionInDto extends BaseInDto implements Serializable {
    private String stationToken;
    private Boolean value;
    private ActionType actionType;
}
