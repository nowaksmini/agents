package com.mini.smartroad.model;

import javax.persistence.*;

@Entity
@Table(name = "stations")
public class StationEntity extends BaseEntity<StationEntity> {

    @Column(name = "name", nullable = false)
    private String name;
    @Column(name = "secret_code", nullable = false)
    private String secretCode;
    @Column(name = "user_name", nullable = false, unique = true)
    private String userName;

    @Column(name = "email")
    private String email;
    @Column(name = "token", unique = true, nullable = false)
    private String token;
    @Column(name = "phone")
    private String phone;
    @Column(name = "longitude", nullable = false)
    private Double longitude;
    @Column(name = "latitude", nullable = false)
    private Double latitude;
    @Column(name = "logo")
    private String logo;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "address_id")
    private AddressEntity address;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSecretCode() {
        return secretCode;
    }

    public void setSecretCode(String secretCode) {
        this.secretCode = secretCode;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public AddressEntity getAddress() {
        return address;
    }

    public void setAddress(AddressEntity address) {
        this.address = address;
    }

    @Override
    public String toString() {
        return "StationEntity{" +
                "name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", token='" + token + '\'' +
                ", phone='" + phone + '\'' +
                ", longitude=" + longitude +
                ", latitude=" + latitude +
                ", logo='" + logo + '\'' +
                ", address=" + address +
                ", secretCode='" + secretCode + '\'' +
                ", userName='" + userName + '\'' +
                ", id=" + id +
                ", createDate=" + createDate +
                ", modifyDate=" + modifyDate +
                '}';
    }
}
