package com.example.coupon.management.exceptions;

import com.example.coupon.management.enums.coupon.CouponErrorDetail;

public class CouponException extends APIException {
    public CouponException(CouponErrorDetail errorDetail) {
        super(errorDetail.getCode(), errorDetail.getMessage(), errorDetail.getDetails());
    }
}