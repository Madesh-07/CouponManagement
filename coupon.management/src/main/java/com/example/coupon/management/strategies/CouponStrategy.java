package com.example.coupon.management.strategies;

import com.example.coupon.management.model.Coupon;

import java.util.List;

public interface CouponStrategy {
    public List<? extends Coupon> getAllCoupons() throws Exception;
    public List<? extends Coupon> getValidCoupons() throws Exception;
}
