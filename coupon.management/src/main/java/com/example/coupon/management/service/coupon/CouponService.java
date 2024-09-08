package com.example.coupon.management.service.coupon;

import com.example.coupon.management.CouponRequest;
import com.example.coupon.management.model.Cart;
import com.example.coupon.management.model.Coupon;

import java.util.List;

public interface CouponService {
    public Coupon addCoupon(CouponRequest couponRqst) throws Exception;
    public Coupon getCoupon(int couponId) throws Exception;
    public Coupon updateCoupon(Coupon coupon) throws Exception;
    public boolean deleteCoupon(int couponId) throws Exception;
    public List<Coupon> getAllCoupons() throws Exception;
    public List<Coupon> getApplicableCoupons(Cart cart) throws Exception;
    public Cart applyCoupon(int couponId,Cart cart) throws Exception;
}
