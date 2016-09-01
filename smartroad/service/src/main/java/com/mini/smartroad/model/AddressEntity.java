package com.mini.smartroad.model;

import javax.persistence.*;

@Entity
@Table(name = "addresses")
public class AddressEntity extends BaseEntity<AddressEntity> {

    @Column(name = "country")
    private String country;
    @Column(name = "distinct")
    private String distinct;
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
    private UserEntity userEntity;

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getDistinct() {
        return distinct;
    }

    public void setDistinct(String distinct) {
        this.distinct = distinct;
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

    public UserEntity getUserEntity() {
        return userEntity;
    }

    public void setUserEntity(UserEntity userEntity) {
        this.userEntity = userEntity;
    }

    @Override
    public String toString() {
        return "AddressEntity{" +
                "country='" + country + '\'' +
                ", distinct='" + distinct + '\'' +
                ", city='" + city + '\'' +
                ", street='" + street + '\'' +
                ", number='" + number + '\'' +
                ", extraNumber='" + extraNumber + '\'' +
                ", postalCode='" + postalCode + '\'' +
                "} " + super.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        AddressEntity that = (AddressEntity) o;

        if (country != null ? !country.equals(that.country) : that.country != null) return false;
        if (distinct != null ? !distinct.equals(that.distinct) : that.distinct != null) return false;
        if (city != null ? !city.equals(that.city) : that.city != null) return false;
        if (street != null ? !street.equals(that.street) : that.street != null) return false;
        if (number != null ? !number.equals(that.number) : that.number != null) return false;
        if (extraNumber != null ? !extraNumber.equals(that.extraNumber) : that.extraNumber != null) return false;
        return postalCode != null ? postalCode.equals(that.postalCode) : that.postalCode == null;

    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (country != null ? country.hashCode() : 0);
        result = 31 * result + (distinct != null ? distinct.hashCode() : 0);
        result = 31 * result + (city != null ? city.hashCode() : 0);
        result = 31 * result + (street != null ? street.hashCode() : 0);
        result = 31 * result + (number != null ? number.hashCode() : 0);
        result = 31 * result + (extraNumber != null ? extraNumber.hashCode() : 0);
        result = 31 * result + (postalCode != null ? postalCode.hashCode() : 0);
        return result;
    }
}
