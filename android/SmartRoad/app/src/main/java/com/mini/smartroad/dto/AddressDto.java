package com.mini.smartroad.dto;

import java.io.Serializable;

public class AddressDto implements Serializable {
    private String country;
    private String distinct;
    private String city;
    private String street;
    private String number;
    private String extraNumber;
    private String postalCode;

    public AddressDto(String country, String distinct, String city, String street, String number, String extraNumber, String postalCode) {
        this.country = country;
        this.distinct = distinct;
        this.city = city;
        this.street = street;
        this.number = number;
        this.extraNumber = extraNumber;
        this.postalCode = postalCode;
    }

    public String getCountry() {
        return country;
    }

    public String getDistinct() {
        return distinct;
    }

    public String getCity() {
        return city;
    }

    public String getStreet() {
        return street;
    }

    public String getNumber() {
        return number;
    }

    public String getExtraNumber() {
        return extraNumber;
    }

    public String getPostalCode() {
        return postalCode;
    }

    @Override
    public String toString() {
        return "AddressDto{" +
                "country='" + country + '\'' +
                ", distinct='" + distinct + '\'' +
                ", city='" + city + '\'' +
                ", street='" + street + '\'' +
                ", number='" + number + '\'' +
                ", extraNumber='" + extraNumber + '\'' +
                ", postalCode='" + postalCode + '\'' +
                '}';
    }
}
