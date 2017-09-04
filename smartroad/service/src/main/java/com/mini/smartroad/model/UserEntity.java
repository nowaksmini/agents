package com.mini.smartroad.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "users")
@Data
public class UserEntity extends BaseEntity<UserEntity> {

    @Column(name = "first_name")
    private String firstName;
    @Column(name = "last_name")
    private String lastName;
    @Column(name = "email", unique = true, nullable = false)
    private String email;
    @Column(name = "password", nullable = false)
    private String password;
    @Column(name = "token", unique = true, nullable = false)
    private String token;
    @Column(name = "points", nullable = false)
    private Integer points = 0;
    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
    private UserPreferencesEntity preferences;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    private List<ActionEntity> actions;
}
