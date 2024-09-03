package com.example.coupon.management.coupons;

import com.example.coupon.management.CouponType;
import com.example.coupon.management.model.Coupon;

import java.util.Date;

public class ProductWiseCoupon extends Coupon {
    private int productId;
    private double discountPercentage;

    public ProductWiseCoupon(Long couponId, int productId, double discountPercentage, String description, Date expirationDate, int repetitionLimit) {
        super(couponId, CouponType.PRODUCT_WISE.getValue(), description,expirationDate, repetitionLimit);
        this.productId = productId;
        this.discountPercentage = discountPercentage;
    }
    public int getProductId() {
        return productId;
    }
    public double getDiscountPercentage() {
        return discountPercentage;
    }
}
