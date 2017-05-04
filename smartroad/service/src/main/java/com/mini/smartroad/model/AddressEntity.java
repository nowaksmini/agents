package com.mini.smartroad.model;

import javax.persistence.*;

@Entity
@Table(name = "addresses")
public class AddressEntity extends BaseEntity<AddressEntity> {

    @Column(name = "country")
    private String country;
    @Column(name = "province")
    private String province;
    @Column(name = "city")
    private String city;
    @Column(name = "street")
    private String street;
    @Column(name = "number")
    private String number;
    @Column(name = "extraNumber")
    private String extraNumber;
    @Column(name = "postalCode")
    private String postalCode;

    @OneToOne(mappedBy = "address")
    private StationDetailsEntity stationDetails;

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getExtraNumber() {
        return extraNumber;
    }

    public void setExtraNumber(String extraNumber) {
        this.extraNumber = extraNumber;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public StationDetailsEntity getStationDetails() {
        return stationDetails;
    }

    public void setStationDetails(StationDetailsEntity stationDetails) {
        this.stationDetails = stationDetails;
    }

    @Override
    public String toString() {
        return "AddressEntity{" +
                "country='" + country + '\'' +
                ", province='" + province + '\'' +
                ", id=" + id +
                ", city='" + city + '\'' +
                ", street='" + street + '\'' +
                ", createDate=" + createDate +
                ", number='" + number + '\'' +
                ", extraNumber='" + extraNumber + '\'' +
                ", modifyDate=" + modifyDate +
                ", postalCode='" + postalCode + '\'' +
                ", stationDetails=" + stationDetails +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof AddressEntity)) return false;
        if (!super.equals(o)) return false;

        AddressEntity that = (AddressEntity) o;

        if (getCountry() != null ? !getCountry().equals(that.getCountry()) : that.getCountry() != null) return false;
        if (getProvince() != null ? !getProvince().equals(that.getProvince()) : that.getProvince() != null)
            return false;
        if (getCity() != null ? !getCity().equals(that.getCity()) : that.getCity() != null) return false;
        if (getStreet() != null ? !getStreet().equals(that.getStreet()) : that.getStreet() != null) return false;
        if (getNumber() != null ? !getNumber().equals(that.getNumber()) : that.getNumber() != null) return false;
        if (getExtraNumber() != null ? !getExtraNumber().equals(that.getExtraNumber()) : that.getExtraNumber() != null)
            return false;
        if (getPostalCode() != null ? !getPostalCode().equals(that.getPostalCode()) : that.getPostalCode() != null)
            return false;
        return getStationDetails() != null ? getStationDetails().equals(that.getStationDetails()) : that.getStationDetails() == null;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (getCountry() != null ? getCountry().hashCode() : 0);
        result = 31 * result + (getProvince() != null ? getProvince().hashCode() : 0);
        result = 31 * result + (getCity() != null ? getCity().hashCode() : 0);
        result = 31 * result + (getStreet() != null ? getStreet().hashCode() : 0);
        result = 31 * result + (getNumber() != null ? getNumber().hashCode() : 0);
        result = 31 * result + (getExtraNumber() != null ? getExtraNumber().hashCode() : 0);
        result = 31 * result + (getPostalCode() != null ? getPostalCode().hashCode() : 0);
        result = 31 * result + (getStationDetails() != null ? getStationDetails().hashCode() : 0);
        return result;
    }
}
