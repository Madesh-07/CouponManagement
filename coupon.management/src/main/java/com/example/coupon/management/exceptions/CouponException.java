package com.example.coupon.management.exceptions;

import com.example.coupon.management.enums.coupon.CouponAPIConstant;

public class CouponException extends APIException {
    public CouponException(CouponAPIConstant errorDetail) {
        super(errorDetail.getCode(), errorDetail.getMessage(), errorDetail.getDetails());
    }
}