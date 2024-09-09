package com.example.coupon.management.strategies;

import com.example.coupon.management.model.Cart;
import com.example.coupon.management.model.Coupon;
import org.json.JSONObject;

import java.util.List;

public interface CouponStrategy {
    public List<? extends Coupon> getAllCoupons() throws Exception;
    public List<? extends Coupon> getValidCoupons() throws Exception;
    public List<? extends Coupon> getApplicableCoupons(Cart cart) throws Exception;
    public Cart applyCoupon(Coupon coupon,Cart cart) throws Exception;
}
