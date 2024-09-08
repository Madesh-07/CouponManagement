package com.example.coupon.management.enums.common;

public enum CommonAPIConstant implements APIConstant {
    SUCCESS(200, "SUCCESS", "Success"),
    FAILURE(400, "FAILURE", "Failure"),
    INVALID_REQUEST(400, "INVALID_REQUEST", "Invalid Request"),
    INTERNAL_SERVER_ERROR(500, "INTERNAL_SERVER_ERROR", "Internal Server Error");

    private final int code;
    private final String message;
    private final String details;

    CommonAPIConstant(int code, String message, String details) {
        this.code = code;
        this.message = message;
        this.details = details;
    }

    public int getCode() {
        return code;
    }

    public String getDetails() {
        return details;
    }

    public String getMessage() {
        return message;
    }
}