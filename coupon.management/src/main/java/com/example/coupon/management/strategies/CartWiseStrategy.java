package com.example.coupon.management.strategies;

import com.example.coupon.management.CartWiseCoupon;
import com.example.coupon.management.model.Coupon;

import java.util.List;

public class CartWiseStrategy implements CouponStrategy{
    @Override
    public List<CartWiseCoupon> getAllCoupons() throws Exception {
        return List.of();
    }

    @Override
    public List<CartWiseCoupon> getValidCoupons() throws Exception {
        return List.of();
    }
}
