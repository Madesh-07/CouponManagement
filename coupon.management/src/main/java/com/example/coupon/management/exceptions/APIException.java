package com.example.coupon.management.exceptions;

import com.example.coupon.management.enums.common.CommonErrorDetail;

public class APIException extends Exception {
    private CommonErrorDetail commonErrordetail;
    private int code;
    private String message;
    private String details;

    public APIException(CommonErrorDetail commonErrordetail) {
        super();
        this.code = commonErrordetail.getCode();
        this.message = commonErrordetail.getMessage();
        this.details = commonErrordetail.getDetails();
    }
    public APIException(int code,String message,String details) {
        super();
        this.code = code;
        this.message = message;
        this.details = details;
    }
}