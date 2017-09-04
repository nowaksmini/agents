package com.mini.smartroad.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;

@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "user_preferences")
@Data
public class UserPreferencesEntity extends BaseEntity<UserPreferencesEntity> {

    @Column(name = "minimal_minutes_left", nullable = false)
    private Integer minimalMinutesLeft = 30;
    @Column(name = "start_searching_minutes_left", nullable = false)
    private Integer startSearchingMinutesLeft = 60;
    @Column(name = "accept_always", nullable = false)
    private Boolean acceptAlways = false;
    @Column(name = "avoided_station_names", nullable = false)
    private String avoidedStationNames = "";
    @Column(name = "preferred_station_names", nullable = false)
    private String preferredStationNames = "";

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id")
    private UserEntity user;
}
