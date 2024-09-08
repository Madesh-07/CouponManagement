package com.example.coupon.management.coupons;

import com.example.coupon.management.enums.coupon.CouponType;
import com.example.coupon.management.model.Coupon;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.time.LocalDate;

public class ProductWiseCoupon extends Coupon {
    @Field("product_id")
    @JsonProperty("product_id")
    private int productId;
    @Field("discount_percentage")
    @JsonProperty("discount_percentage")
    private double discountPercentage;

    public ProductWiseCoupon(){}

    public ProductWiseCoupon(int productId, double discountPercentage, String description, LocalDate expirationDate,String code) {
        super(CouponType.PRODUCT_WISE.getValue(), description,expirationDate,code);
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
