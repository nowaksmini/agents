package com.mini.smartroad.model;

import com.sun.istack.internal.NotNull;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "actions")
public class ActionEntity extends BaseEntity<ActionEntity> {

    @NotNull
    @Column(name = "token", unique = true)
    private String token;

    @Column(name = "value")
    private Boolean value;
    @Column(name = "sent_confirm")
    private Boolean sentConfirm;

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
    private StationEntity station;

    /*
  [token]        [varchar](100)        NOT NULL UNIQUE,
  [action_type]  [varchar](100)        NOT NULL,
  [sent_confirm] [bit]                 NOT NULL,
  [station_id]   [int]                 NOT NULL,
  [user_id]      [int]                 NOT NULL,
  [from]         [datetime]            NOT NULL,
  [to]           [datetime]            NOT NULL,
     */
}
