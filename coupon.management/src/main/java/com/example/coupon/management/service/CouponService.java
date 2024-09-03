package com.example.coupon.management.service;

import com.example.coupon.management.model.Coupon;

import java.util.List;

public interface CouponService {
    public Coupon addCoupon(Coupon coupon) throws Exception;
    public Coupon getCoupon(Long couponId) throws Exception;
    public Coupon updateCoupon(Coupon coupon) throws Exception;
    public List<Coupon> getAllCoupons() throws Exception;
    public List<Coupon> getApplicableCoupons(Long cartId) throws Exception;
    public List<Coupon> applyCoupons(Long cartId) throws Exception;
}
