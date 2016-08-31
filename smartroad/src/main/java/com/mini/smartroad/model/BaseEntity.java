package com.mini.smartroad.model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@MappedSuperclass
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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getCreateDate() {
        if (createDate != null) {
            return new Date(createDate.getTime());
        } else {
            return null;
        }
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Date getModifyDate() {
        if (modifyDate != null) {
            return new Date(modifyDate.getTime());
        } else {
            return null;
        }
    }

    public void setModifyDate(Date modifyDate) {
        this.modifyDate = modifyDate;
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

