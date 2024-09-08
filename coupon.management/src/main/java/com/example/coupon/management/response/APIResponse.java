package com.example.coupon.management.response;

import com.example.coupon.management.enums.common.APIConstant;
import com.example.coupon.management.enums.common.CommonAPIConstant;
import com.example.coupon.management.exceptions.APIException;

public class APIResponse<T extends APIConstant> {
    private int code;
    private String message;
    private String details;

    public APIResponse(APIException exception) {
        this.code = exception.getCode();
        this.message = exception.getMessage();
        this.details = exception.getDetails();
    }
    public APIResponse(T apiConstant){
        this.code = apiConstant.getCode();
        this.message = apiConstant.getMessage();
        this.details = apiConstant.getDetails();
    }

    // Getters and setters
    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }
}