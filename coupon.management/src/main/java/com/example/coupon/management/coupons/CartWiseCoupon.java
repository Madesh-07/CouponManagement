package com.example.coupon.management;

import com.example.coupon.management.enums.coupon.CouponType;
import com.example.coupon.management.model.Coupon;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;

public class CartWiseCoupon extends Coupon {
    @JsonProperty("threshold")
    private double threshold;
    @JsonProperty("discount_percentage")
    private double discountPercentage;

    public CartWiseCoupon() {
        super();
    }
    public CartWiseCoupon(double threshold, String description, LocalDate expirationDate, double discountPercentage,String code) {
        super(CouponType.CART_WISE.getValue(),description, expirationDate,code); // Discount field is not used in the parent class
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