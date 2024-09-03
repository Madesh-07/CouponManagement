package com.example.coupon.management;

import com.example.coupon.management.model.Coupon;

import java.util.Date;

public class CartWiseCoupon extends Coupon {
    private double minimumCartTotalPrice;
    private double discountPercentage;

    public CartWiseCoupon(Long couponId, double minimumCartTotalPrice, String descrption, Date expirationDate, double discountPercentage,int repitionLimit) {
        super(couponId, CouponType.CART_WISE.getValue(),descrption, expirationDate,repitionLimit); // Discount field is not used in the parent class
        this.minimumCartTotalPrice = minimumCartTotalPrice;
        this.discountPercentage = discountPercentage;
    }

    public double getMinimumCartTotalPrice() {
        return minimumCartTotalPrice;
    }

    public double getDiscountPercentage() {
        return discountPercentage;
    }
}