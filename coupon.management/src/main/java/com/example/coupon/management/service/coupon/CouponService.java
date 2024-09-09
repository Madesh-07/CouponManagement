package com.example.coupon.management.service.coupon;

import com.example.coupon.management.CouponRequest;
import com.example.coupon.management.model.Cart;
import com.example.coupon.management.model.Coupon;

import java.util.List;
import java.util.Locale;

public interface CouponService {
    public Coupon addCoupon(CouponRequest couponRqst) throws Exception;
    public Coupon getCoupon(int couponId) throws Exception;
    public Coupon updateCoupon(int couponId,CouponRequest couponRqst) throws Exception;
    public boolean deleteCoupon(int couponId) throws Exception;
    public List<Coupon> getAllCoupons(Locale locale) throws Exception;
    public List<Coupon> getApplicableCoupons(Cart cart) throws Exception;
    public Cart applyCoupon(int couponId,Cart cart) throws Exception;
}
