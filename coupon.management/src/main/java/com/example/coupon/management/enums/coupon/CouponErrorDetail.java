package com.example.coupon.management.enums.coupon;

public enum CouponErrorDetail {
    COUPON_NOT_FOUND(404, "COUPON_NOT_FOUND", "Coupon not found"),
    COUPON_EXPIRED(403, "COUPON_EXPIRED", "Coupon has expired!"),
    COUPON_NOT_APPLICABLE(422, "COUPON_NOT_APPLICABLE", "Coupon is not applicable.Try with other coupons."),
    COUPON_LIMIT_REACHED(429, "COUPON_LIMIT_REACHED", "Coupon repetition limit has been reached.Try with other coupons."),
    INVALID_COUPON_DETAILS(400, "INVALID_COUPON_DETAILS", "Invalid coupon details.Kindly revalidate it once.");

    private final int code;
    private final String message;
    private final String details;

    CouponErrorDetail(int code, String message, String details) {
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
}