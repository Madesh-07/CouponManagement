package com.example.coupon.management.strategies;

import com.example.coupon.management.coupons.BuyXGetYCoupon;
import com.example.coupon.management.model.Coupon;

import java.util.List;

public class BuyXGetYStrategy implements CouponStrategy {
    @Override
    public List<BuyXGetYCoupon> getAllCoupons() throws Exception {
        return List.of();
    }

    @Override
    public List<BuyXGetYCoupon> getValidCoupons() throws Exception {
        return List.of();
    }
}
