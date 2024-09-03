package com.example.coupon.management;

public enum CouponType {
    CART_WISE("cart-wise"),
    PRODUCT_WISE("product-wise"),
    BXGY("bxgy");

    private final String value;

    CouponType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}