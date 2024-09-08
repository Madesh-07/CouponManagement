package com.example.coupon.management;


import com.example.coupon.management.model.Coupon;
import com.fasterxml.jackson.databind.JsonNode;

public class CouponRequest<T extends Coupon> {
    private String type;
    private JsonNode details;

    public CouponRequest() {}

    public CouponRequest(String type, JsonNode details) {
        this.type = type;
        this.details = details;
    }

    public String getType() {
        return type;
    }

    public JsonNode getDetails() {
        return details;
    }
}