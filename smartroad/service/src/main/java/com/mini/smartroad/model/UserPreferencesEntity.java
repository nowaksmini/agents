package com.mini.smartroad.model;

import javax.persistence.*;

@Entity
@Table(name = "user_preferences")
public class UserPreferencesEntity extends BaseEntity<UserPreferencesEntity> {

    @Column(name = "minimal_minutes_left", nullable = false)
    private Integer minimalMinutesLeft = 30;
    @Column(name = "start_searching_minutes_left", nullable = false)
    private Integer startSearchingMinutesLeft = 60;
    @Column(name = "accept_always", nullable = false)
    private Boolean acceptAlways = false;
    @OneToOne
    @JoinColumn(name = "user_id")
    private UserEntity user;

    public Integer getMinimalMinutesLeft() {
        return minimalMinutesLeft;
    }

    public void setMinimalMinutesLeft(Integer minimalMinutesLeft) {
        this.minimalMinutesLeft = minimalMinutesLeft;
    }

    public Integer getStartSearchingMinutesLeft() {
        return startSearchingMinutesLeft;
    }

    public void setStartSearchingMinutesLeft(Integer startSearchingMinutesLeft) {
        this.startSearchingMinutesLeft = startSearchingMinutesLeft;
    }

    public Boolean getAcceptAlways() {
        return acceptAlways;
    }

    public void setAcceptAlways(Boolean acceptAlways) {
        this.acceptAlways = acceptAlways;
    }

    public UserEntity getUser() {
        return user;
    }

    public void setUser(UserEntity user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "UserPreferencesEntity{" +
                "minimalMinutesLeft=" + minimalMinutesLeft +
                ", id=" + id +
                ", startSearchingMinutesLeft=" + startSearchingMinutesLeft +
                ", createDate=" + createDate +
                ", acceptAlways=" + acceptAlways +
                ", modifyDate=" + modifyDate +
                ", user=" + user +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof UserPreferencesEntity)) return false;
        if (!super.equals(o)) return false;

        UserPreferencesEntity that = (UserPreferencesEntity) o;

        if (getMinimalMinutesLeft() != null ? !getMinimalMinutesLeft().equals(that.getMinimalMinutesLeft()) : that.getMinimalMinutesLeft() != null)
            return false;
        if (getStartSearchingMinutesLeft() != null ? !getStartSearchingMinutesLeft().equals(that.getStartSearchingMinutesLeft()) : that.getStartSearchingMinutesLeft() != null)
            return false;
        if (getAcceptAlways() != null ? !getAcceptAlways().equals(that.getAcceptAlways()) : that.getAcceptAlways() != null)
            return false;
        return getUser() != null ? getUser().equals(that.getUser()) : that.getUser() == null;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (getMinimalMinutesLeft() != null ? getMinimalMinutesLeft().hashCode() : 0);
        result = 31 * result + (getStartSearchingMinutesLeft() != null ? getStartSearchingMinutesLeft().hashCode() : 0);
        result = 31 * result + (getAcceptAlways() != null ? getAcceptAlways().hashCode() : 0);
        result = 31 * result + (getUser() != null ? getUser().hashCode() : 0);
        return result;
    }
}
