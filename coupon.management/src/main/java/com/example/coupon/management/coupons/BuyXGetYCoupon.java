package com.example.coupon.management.coupons;

import com.example.coupon.management.CouponType;
import com.example.coupon.management.model.Coupon;
import com.example.coupon.management.model.Product;

import java.util.Date;
import java.util.List;

public class BuyXGetYCoupon extends Coupon {
    private List<Product> buyProducts;
    private List<Product> getProducts;
    private int buyQuantity;
    private int getQuantity;

    public BuyXGetYCoupon(Long couponId, List<Product> buyProducts, List<Product> getProducts, int buyQuantity, int getQuantity, String description, Date expirationDate, int repetitionLimit) {
        super(couponId, CouponType.BXGY.getValue(), description, expirationDate, repetitionLimit);
        this.buyProducts = buyProducts;
        this.getProducts = getProducts;
        this.buyQuantity = buyQuantity;
        this.getQuantity = getQuantity;
    }
}