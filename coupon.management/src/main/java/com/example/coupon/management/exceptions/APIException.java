package com.example.coupon.management.exceptions;

import com.example.coupon.management.enums.common.CommonAPIConstant;
import com.example.coupon.management.enums.coupon.CouponAPIConstant;

public class APIException extends Exception {
    private int code;
    private String message;
    private String details;

    public APIException(CommonAPIConstant commonAPIConstant) {
        super();
        this.code = commonAPIConstant.getCode();
        this.message = commonAPIConstant.getMessage();
        this.details = commonAPIConstant.getDetails();
    }
    public APIException(int code,String message,String details) {
        super();
        this.code = code;
        this.message = message;
        this.details = details;
    }
    public int getCode() {
        return code;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public String getDetails() {
        return details;
    }
}