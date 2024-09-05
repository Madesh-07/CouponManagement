package com.example.coupon.management;


public class CouponRequest {
    private String type;
    private Object details;

    public CouponRequest() {}

    public CouponRequest(String type, Object details) {
        this.type = type;
        this.details = details;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Object getDetails() {
        return details;
    }

    public void setDetails(Object details) {
        this.details = details;
    }
}