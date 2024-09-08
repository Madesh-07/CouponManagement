package com.example.coupon.management.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.*;

import java.time.LocalDate;
import java.util.List;

@Document("coupons")
public class Coupon{
    @Id
    @Field("couponId")
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
    private double discount;

    public Coupon(){
    }
    public Coupon(String type, String description, LocalDate expirationDate,String code) {
        this.type = type;
        this.description = description;
        this.expirationDate = expirationDate;// default to active
        this.code = code;
    }

    public int getCouponId() {
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

    public String getCode() {
        return code;
    }

    public double getDiscount() {
        return discount;
    }

    public void setDiscount(double discount) {
        this.discount = discount;
    }

}