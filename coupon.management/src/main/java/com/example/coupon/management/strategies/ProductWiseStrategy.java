package com.example.coupon.management.strategies;

import com.example.coupon.management.coupons.ProductWiseCoupon;
import com.example.coupon.management.model.Coupon;

import java.util.List;

public class ProductWiseStrategy implements CouponStrategy {
    @Override
    public List<ProductWiseCoupon> getAllCoupons() throws Exception {
        return List.of();
    }

    @Override
    public List<ProductWiseCoupon> getValidCoupons() throws Exception {
        return List.of();
    }

    public int calculateDiscount(Long productId,ProductWiseCoupon coupon){
        return 1;
    }
}
