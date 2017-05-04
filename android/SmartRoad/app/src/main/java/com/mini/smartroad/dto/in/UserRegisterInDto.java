package com.mini.smartroad.dto.in;

import java.io.Serializable;

public class UserRegisterInDto implements Serializable {

    private String email;
    private String firstName;
    private String lastName;
    private String password;

    public UserRegisterInDto(String email, String firstName, String lastName, String password) {
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getPassword() {
        return password;
    }

    @Override
    public String toString() {
        return "UserRegisterInDto{" +
                "password='" + password + '\'' +
                ", lastName='" + lastName + '\'' +
                ", firstName='" + firstName + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
