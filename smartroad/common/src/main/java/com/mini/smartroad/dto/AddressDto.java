package com.mini.smartroad.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

@Data
@AllArgsConstructor
public class AddressDto implements Serializable {
    private String country;
    private String distinct;
    private String city;
    private String street;
    private String number;
    private String extraNumber;
    private String postalCode;
}