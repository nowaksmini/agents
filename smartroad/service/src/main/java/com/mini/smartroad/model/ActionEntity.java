package com.mini.smartroad.model;

import com.mini.smartroad.common.ActionType;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.util.Date;

@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "actions")
@Data
public class ActionEntity extends BaseEntity<ActionEntity> {

    @Column(name = "token", unique = true, nullable = false)
    private String token;
    @Column(name = "value")
    private Boolean value;
    @Column(name = "sent_confirm")
    private Boolean sentConfirm;
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "date_from", nullable = false)
    private Date dateFrom;
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "date_to", nullable = false)
    private Date dateTo;
    @Enumerated(EnumType.STRING)
    @Column(name = "action_type", nullable = false)
    private ActionType actionType;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "station_id", nullable = false)
    private StationEntity stationDetails;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "user_id", nullable = false)
    private UserEntity user;
}
