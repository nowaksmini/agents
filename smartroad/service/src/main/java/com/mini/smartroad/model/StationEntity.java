package com.mini.smartroad.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;

@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "stations")
@Data
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

    @OneToOne(cascade = CascadeType.ALL, mappedBy = "station")
    private StationStrategyEntity stationStrategy;
}
