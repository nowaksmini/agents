package com.mini.smartroad.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;

@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "station_strategy")
@Data
public class StationStrategyEntity extends BaseEntity<StationStrategyEntity> {

    @Column(name = "group_size_0", nullable = false)
    private Integer groupSize0 = 2;
    @Column(name = "group_size_1", nullable = false)
    private Integer groupSize1 = 5;
    @Column(name = "group_size_2", nullable = false)
    private Integer groupSize2 = 7;
    @Column(name = "group_size_3", nullable = false)
    private Integer groupSize3 = 10;
    @Column(name = "group_size_4", nullable = false)
    private Integer groupSize4 = 15;

    @Column(name = "points_group_size_0", nullable = false)
    private Integer pointsGroupSize0 = 10;
    @Column(name = "points_group_size_1", nullable = false)
    private Integer pointsGroupSize1 = 60;
    @Column(name = "points_group_size_2", nullable = false)
    private Integer pointsGroupSize2 = 90;
    @Column(name = "points_group_size_3", nullable = false)
    private Integer pointsGroupSize3 = 150;
    @Column(name = "points_group_size_4", nullable = false)
    private Integer pointsGroupSize4 = 300;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "station_id")
    private StationEntity station;
}
