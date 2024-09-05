package com.example.coupon.management.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.time.LocalDate;
import java.util.List;

@Document("coupons")
public class Coupon {
    @Id
    @Field("_couponid")
    private Long couponId;

    @Indexed(unique = true)
    @Field("type")
    private String type;

    @Field("description")
    private String description;

    @Field("expiration_date")
    private LocalDate expirationDate;

    @Field("status")
    private boolean status;

    public Coupon(Long couponId, String type, String description, LocalDate expirationDate) {
        this.couponId = couponId;
        this.type = type;
        this.description = description;
        this.expirationDate = expirationDate;
        this.status = true; // default to active
    }

    public Long getCouponId() {
        return couponId;
    }

    public String getType() {
        return type;
    }

    public String getDescription() {
        return description;
    }

    public LocalDate getExpirationDate() {
        return expirationDate;
    }
    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }
}