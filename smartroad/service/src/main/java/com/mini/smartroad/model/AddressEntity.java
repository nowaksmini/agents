package com.mini.smartroad.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;

@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "addresses")
@Data
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
    private StationEntity stationDetails;
}
