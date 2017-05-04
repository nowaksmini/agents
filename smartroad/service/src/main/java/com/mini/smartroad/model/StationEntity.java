package com.mini.smartroad.model;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "stations")
public class StationEntity extends BaseEntity<StationEntity> {
    @Column(name = "name", nullable = false)
    private String name;
    @Column(name = "full_name")
    private String fullName;
    @Column(name = "logo")
    private String logo;
    @Column(name = "token", unique = true, nullable = false)
    private String token;

    @OneToMany(mappedBy = "station", fetch = FetchType.LAZY)
    private List<StationDetailsEntity> stationDetails;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public List<StationDetailsEntity> getStationDetails() {
        return stationDetails;
    }

    public void setStationDetails(List<StationDetailsEntity> stationDetails) {
        this.stationDetails = stationDetails;
    }

    @Override
    public String toString() {
        return "StationEntity{" +
                "name='" + name + '\'' +
                ", id=" + id +
                ", fullName='" + fullName + '\'' +
                ", logo='" + logo + '\'' +
                ", createDate=" + createDate +
                ", token='" + token + '\'' +
                ", modifyDate=" + modifyDate +
                ", stationDetails=" + stationDetails +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof StationEntity)) return false;
        if (!super.equals(o)) return false;

        StationEntity that = (StationEntity) o;

        if (getName() != null ? !getName().equals(that.getName()) : that.getName() != null) return false;
        if (getFullName() != null ? !getFullName().equals(that.getFullName()) : that.getFullName() != null)
            return false;
        if (getLogo() != null ? !getLogo().equals(that.getLogo()) : that.getLogo() != null) return false;
        return getToken() != null ? getToken().equals(that.getToken()) : that.getToken() == null;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (getName() != null ? getName().hashCode() : 0);
        result = 31 * result + (getFullName() != null ? getFullName().hashCode() : 0);
        result = 31 * result + (getLogo() != null ? getLogo().hashCode() : 0);
        result = 31 * result + (getToken() != null ? getToken().hashCode() : 0);
        return result;
    }
}
