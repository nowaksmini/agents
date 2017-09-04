package com.mini.smartroad.model;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@MappedSuperclass
@Data
public abstract class BaseEntity<T extends BaseEntity> implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Long id;
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "create_date")
    protected Date createDate = new Date();
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "modify_date")
    protected Date modifyDate = new Date();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        BaseEntity<?> that = (BaseEntity<?>) o;

        return getId() != null ? getId().equals(that.getId()) : that.getId() == null;
    }

    @Override
    public int hashCode() {
        return getId() != null ? getId().hashCode() : 0;
    }

    @PreUpdate
    public void preUpdate() {
        modifyDate = new Date();
    }

    @PrePersist
    public void prePersist() {
        createDate = new Date();
    }
}

