package com.mini.smartroad.dto.in.configure;

import com.mini.smartroad.dto.in.BaseInDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
public class StationPreferencesInDto extends BaseInDto {
    private String name;
    private String email;
    private String phone;
    private String logo;

    public StationPreferencesInDto(String token, String name, String email, String phone, String logo) {
        super(token);
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.logo = logo;
    }
}
