package com.example.coupon.management.model;

import java.util.Date;

public class Coupon {
    private Long couponId;
    private String name;
    private String description;
    private Date expirationDate;
    private int repitionLimit;
    private boolean status;

    public Coupon(Long couponId, String name, String description, Date expirationDate,int repitionLimit) {
        this.couponId = couponId;
        this.name = name;
        this.description = description;
        this.expirationDate = expirationDate;
        this.repitionLimit = repitionLimit;
        this.status = true; // default to active
    }

    public Long getCouponId() {
        return couponId;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public Date getExpirationDate() {
        return expirationDate;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }
}