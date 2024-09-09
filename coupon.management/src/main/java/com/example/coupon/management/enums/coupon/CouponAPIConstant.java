package com.example.coupon.management.enums.coupon;

import com.example.coupon.management.enums.common.APIConstant;
import com.example.coupon.management.enums.common.CommonAPIConstant;

public enum CouponAPIConstant implements APIConstant {
    //Success Messages
    COUPON_CREATED(201,"success","COUPON_CREATED", "Coupon created successfully."),
    COUPON_UPDATED(200,"success", "COUPON_UPDATED", "Coupon updated successfully."),
    COUPON_DELETED(204, "success","COUPON_DELETED", "Coupon deleted successfully."),



    COUPON_NOT_FOUND(404, "failure", "COUPON_NOT_FOUND", "Coupon not found"),
    COUPON_EXPIRED(403,"failure", "COUPON_EXPIRED", "Coupon has expired!"),
    COUPON_NOT_APPLICABLE(422,"failure", "COUPON_NOT_APPLICABLE", "Coupon is not applicable.Try with other coupons."),
    COUPON_LIMIT_REACHED(429, "failure", "COUPON_LIMIT_REACHED", "Coupon repetition limit has been reached.Try with other coupons."),
    NO_APPLICABLE_COUPONS(400, "failure", "NO_APPLICABLE_COUPONS", "No Applicable coupons found for this cart!"),
    NO_COUPONS_FOUND(400,"failure","NO_COUPONS_FOUND","No coupons found");

    private final int code;
    private final String status;
    private final String message;
    private final String details;

    CouponAPIConstant(int code, String status,String message, String details) {
        this.status = status;
        this.code = code;
        this.message = message;
        this.details = details;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public String getDetails() {
        return details;
    }
    public String getStatus(){
        return status;
    }
}