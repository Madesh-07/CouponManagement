package com.example.coupon.management;

import com.example.coupon.management.enums.coupon.CouponType;
import com.example.coupon.management.model.Coupon;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.time.LocalDate;

@Document("cartwise_coupons")
public class CartWiseCoupon extends Coupon {
    @Field("threshold")
    private double threshold;
    @Field("discount")
    private double discountPercentage;

    public CartWiseCoupon(Long couponId, double threshold, String description, LocalDate expirationDate, double discountPercentage) {
        super(couponId, CouponType.CART_WISE.getValue(),description, expirationDate); // Discount field is not used in the parent class
        this.threshold = threshold;
        this.discountPercentage = discountPercentage;
    }

    public double getThreshold() {
        return threshold;
    }

    public double getDiscountPercentage() {
        return discountPercentage;
    }
}