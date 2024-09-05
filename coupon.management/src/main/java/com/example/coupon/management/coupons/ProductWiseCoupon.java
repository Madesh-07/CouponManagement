package com.example.coupon.management.coupons;

import com.example.coupon.management.enums.coupon.CouponType;
import com.example.coupon.management.model.Coupon;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;

@Document("productwise_coupons")
public class ProductWiseCoupon extends Coupon {
    private int productId;
    private double discountPercentage;

    public ProductWiseCoupon(Long couponId, int productId, double discountPercentage, String description, LocalDate expirationDate) {
        super(couponId, CouponType.PRODUCT_WISE.getValue(), description,expirationDate);
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
