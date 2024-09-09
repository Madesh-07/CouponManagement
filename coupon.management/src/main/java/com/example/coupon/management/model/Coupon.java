package com.example.coupon.management.model;

import com.example.coupon.management.dal.CouponDAL;
import com.example.coupon.management.sequence.NextSequenceService;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.*;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.Random;
import java.util.UUID;
import java.util.List;

@Component
@Document("coupons")
public class Coupon{
    @Field("coupon_id")
    private int couponId;

    @Field("type")
    @Indexed(unique = true)
    private String type;

    @Field("description")
    private String description;

    @Field("code")
    private String code;

    @Field("expiration_date")
    @JsonDeserialize(using = LocalDateDeserializer.class)
    @JsonProperty("expiration_date")
    private LocalDate expirationDate;

    @Field("status")
    private boolean status = true;
    @JsonIgnore
    private double discount;

    public Coupon(){}
    public Coupon(String type, String description, LocalDate expirationDate,String code) {
        this.type = type;
        this.description = description;
        this.expirationDate = expirationDate;// default to active
        this.code = code;
    }

    public int getCouponId() {
        return couponId;
    }
    public void setCouponId(int couponId) {
        this.couponId = couponId;
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

    public String getCode() {
        return code;
    }

    public double getDiscount() {
        return discount;
    }

    public void setDiscount(double discount) {
        this.discount = discount;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}