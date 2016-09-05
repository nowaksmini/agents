package com.mini.smartroad.model;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "coupons")
public class CouponEntity extends BaseEntity<CouponEntity>{

    @Column(name = "token", unique = true, nullable = false)
    private String token;
    @Column(name = "value")
    private Boolean value;
    @Column(name = "discount_code")
    private String discountCode;
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "time_of_use")
    private Date timeOfUse;
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "valid_from", nullable = false)
    private Date validFrom;
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "valid_to", nullable = false)
    private Date validTo;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "action_id", nullable = false)
    private ActionEntity action;

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

    public String getDiscountCode() {
        return discountCode;
    }

    public void setDiscountCode(String discountCode) {
        this.discountCode = discountCode;
    }

    public Date getTimeOfUse() {
        return timeOfUse;
    }

    public void setTimeOfUse(Date timeOfUse) {
        this.timeOfUse = timeOfUse;
    }

    public Date getValidFrom() {
        return validFrom;
    }

    public void setValidFrom(Date validFrom) {
        this.validFrom = validFrom;
    }

    public Date getValidTo() {
        return validTo;
    }

    public void setValidTo(Date validTo) {
        this.validTo = validTo;
    }

    public ActionEntity getAction() {
        return action;
    }

    public void setAction(ActionEntity action) {
        this.action = action;
    }

    @Override
    public String toString() {
        return "CouponEntity{" +
                "token='" + token + '\'' +
                ", value=" + value +
                ", discountCode='" + discountCode + '\'' +
                ", timeOfUse=" + timeOfUse +
                ", validFrom=" + validFrom +
                ", validTo=" + validTo +
                ", action=" + action +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CouponEntity)) return false;

        CouponEntity that = (CouponEntity) o;

        if (getToken() != null ? !getToken().equals(that.getToken()) : that.getToken() != null) return false;
        if (getValue() != null ? !getValue().equals(that.getValue()) : that.getValue() != null) return false;
        if (getDiscountCode() != null ? !getDiscountCode().equals(that.getDiscountCode()) : that.getDiscountCode() != null)
            return false;
        if (getTimeOfUse() != null ? !getTimeOfUse().equals(that.getTimeOfUse()) : that.getTimeOfUse() != null)
            return false;
        if (getValidFrom() != null ? !getValidFrom().equals(that.getValidFrom()) : that.getValidFrom() != null)
            return false;
        if (getValidTo() != null ? !getValidTo().equals(that.getValidTo()) : that.getValidTo() != null) return false;
        return getAction() != null ? getAction().equals(that.getAction()) : that.getAction() == null;

    }

    @Override
    public int hashCode() {
        int result = getToken() != null ? getToken().hashCode() : 0;
        result = 31 * result + (getValue() != null ? getValue().hashCode() : 0);
        result = 31 * result + (getDiscountCode() != null ? getDiscountCode().hashCode() : 0);
        result = 31 * result + (getTimeOfUse() != null ? getTimeOfUse().hashCode() : 0);
        result = 31 * result + (getValidFrom() != null ? getValidFrom().hashCode() : 0);
        result = 31 * result + (getValidTo() != null ? getValidTo().hashCode() : 0);
        result = 31 * result + (getAction() != null ? getAction().hashCode() : 0);
        return result;
    }
}
