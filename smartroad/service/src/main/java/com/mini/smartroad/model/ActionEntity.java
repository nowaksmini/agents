package com.mini.smartroad.model;

import com.mini.smartroad.common.ActionType;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "actions")
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

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Boolean getValue() {
        return value;
    }

    public void setValue(Boolean value) {
        this.value = value;
    }

    public Boolean getSentConfirm() {
        return sentConfirm;
    }

    public void setSentConfirm(Boolean sentConfirm) {
        this.sentConfirm = sentConfirm;
    }

    public Date getDateFrom() {
        return dateFrom;
    }

    public void setDateFrom(Date dateFrom) {
        this.dateFrom = dateFrom;
    }

    public Date getDateTo() {
        return dateTo;
    }

    public void setDateTo(Date dateTo) {
        this.dateTo = dateTo;
    }

    public ActionType getActionType() {
        return actionType;
    }

    public void setActionType(ActionType actionType) {
        this.actionType = actionType;
    }

    public StationEntity getStationDetails() {
        return stationDetails;
    }

    public void setStationDetails(StationEntity stationDetails) {
        this.stationDetails = stationDetails;
    }

    public UserEntity getUser() {
        return user;
    }

    public void setUser(UserEntity user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "ActionEntity{" +
                "id=" + id +
                ", token='" + token + '\'' +
                ", value=" + value +
                ", createDate=" + createDate +
                ", sentConfirm=" + sentConfirm +
                ", modifyDate=" + modifyDate +
                ", dateFrom=" + dateFrom +
                ", dateTo=" + dateTo +
                ", actionType=" + actionType +
                ", stationDetails=" + stationDetails +
                ", driver=" + user +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ActionEntity)) return false;
        if (!super.equals(o)) return false;

        ActionEntity that = (ActionEntity) o;

        if (getToken() != null ? !getToken().equals(that.getToken()) : that.getToken() != null) return false;
        if (getValue() != null ? !getValue().equals(that.getValue()) : that.getValue() != null) return false;
        if (getSentConfirm() != null ? !getSentConfirm().equals(that.getSentConfirm()) : that.getSentConfirm() != null)
            return false;
        if (getDateFrom() != null ? !getDateFrom().equals(that.getDateFrom()) : that.getDateFrom() != null)
            return false;
        if (getDateTo() != null ? !getDateTo().equals(that.getDateTo()) : that.getDateTo() != null) return false;
        if (getActionType() != that.getActionType()) return false;
        if (getStationDetails() != null ? !getStationDetails().equals(that.getStationDetails()) : that.getStationDetails() != null)
            return false;
        return getUser() != null ? getUser().equals(that.getUser()) : that.getUser() == null;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (getToken() != null ? getToken().hashCode() : 0);
        result = 31 * result + (getValue() != null ? getValue().hashCode() : 0);
        result = 31 * result + (getSentConfirm() != null ? getSentConfirm().hashCode() : 0);
        result = 31 * result + (getDateFrom() != null ? getDateFrom().hashCode() : 0);
        result = 31 * result + (getDateTo() != null ? getDateTo().hashCode() : 0);
        result = 31 * result + (getActionType() != null ? getActionType().hashCode() : 0);
        result = 31 * result + (getStationDetails() != null ? getStationDetails().hashCode() : 0);
        result = 31 * result + (getUser() != null ? getUser().hashCode() : 0);
        return result;
    }
}
