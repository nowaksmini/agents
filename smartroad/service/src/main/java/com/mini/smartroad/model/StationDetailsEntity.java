package com.mini.smartroad.model;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "stations_details")
public class StationDetailsEntity extends BaseEntity<StationDetailsEntity> {

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
    @JoinColumn(name = "station_id")
    private StationEntity station;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "address_id")
    private AddressEntity address;
    @OneToMany(mappedBy = "stationDetails", fetch = FetchType.LAZY)
    private List<ActionEntity> actions;

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

    public List<ActionEntity> getActions() {
        return actions;
    }

    public void setActions(List<ActionEntity> actions) {
        this.actions = actions;
    }

    public StationEntity getStation() {
        return station;
    }

    public void setStation(StationEntity station) {
        this.station = station;
    }

    @Override
    public String toString() {
        return "StationDetailsEntity{" +
                "email='" + email + '\'' +
                ", token='" + token + '\'' +
                ", phone='" + phone + '\'' +
                ", longitude=" + longitude +
                ", latitude=" + latitude +
                ", logo='" + logo + '\'' +
                ", station=" + station +
                ", address=" + address +
                ", actions=" + actions +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        StationDetailsEntity that = (StationDetailsEntity) o;

        if (email != null ? !email.equals(that.email) : that.email != null) return false;
        if (token != null ? !token.equals(that.token) : that.token != null) return false;
        if (phone != null ? !phone.equals(that.phone) : that.phone != null) return false;
        if (longitude != null ? !longitude.equals(that.longitude) : that.longitude != null) return false;
        if (latitude != null ? !latitude.equals(that.latitude) : that.latitude != null) return false;
        if (logo != null ? !logo.equals(that.logo) : that.logo != null) return false;
        if (station != null ? !station.equals(that.station) : that.station != null) return false;
        return address != null ? address.equals(that.address) : that.address == null;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (email != null ? email.hashCode() : 0);
        result = 31 * result + (token != null ? token.hashCode() : 0);
        result = 31 * result + (phone != null ? phone.hashCode() : 0);
        result = 31 * result + (longitude != null ? longitude.hashCode() : 0);
        result = 31 * result + (latitude != null ? latitude.hashCode() : 0);
        result = 31 * result + (logo != null ? logo.hashCode() : 0);
        result = 31 * result + (station != null ? station.hashCode() : 0);
        result = 31 * result + (address != null ? address.hashCode() : 0);
        return result;
    }
}
