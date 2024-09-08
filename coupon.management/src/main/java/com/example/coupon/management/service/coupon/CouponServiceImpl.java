package com.example.coupon.management.service.coupon;

import com.example.coupon.management.CouponRequest;
import com.example.coupon.management.dao.CouponDAL;
import com.example.coupon.management.exceptions.CouponException;
import com.example.coupon.management.model.Cart;
import com.example.coupon.management.model.Coupon;
import com.example.coupon.management.strategies.BuyXGetYStrategy;
import com.example.coupon.management.strategies.CartWiseStrategy;
import com.example.coupon.management.strategies.CouponStrategy;
import com.example.coupon.management.strategies.ProductWiseStrategy;
import com.example.coupon.management.util.CouponUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class CouponServiceImpl implements CouponService {

    @Autowired
    CouponDAL couponDAL;
    @Autowired
    CouponUtil couponUtil;

    public Coupon addCoupon(CouponRequest couponRqst) throws Exception {
        Coupon coupon = null;
        try {
            coupon = couponUtil.validateCouponDetailsForCreate(couponRqst);
        } catch (CouponException coupExc) {
            throw coupExc;
        }
        return couponDAL.addCoupon(coupon);
    }

    public Coupon getCoupon(int couponId) throws Exception {
        return couponDAL.getCouponById(couponId);
    }

    public Coupon updateCoupon(Coupon coupon) throws Exception {
        return null;
    }

    @Override
    public boolean deleteCoupon(int couponId) throws Exception {
        couponDAL.deleteCouponById(couponId);
        return true;
    }

    public List<Coupon> getAllCoupons() throws Exception {
        return couponDAL.getAllCoupons();
    }

    public List<Coupon> getApplicableCoupons(Cart cart) throws Exception {
        List<CouponStrategy> couponStrategies = new ArrayList<>(Arrays.asList(
                new CartWiseStrategy(cart),
                new ProductWiseStrategy(cart),
                new BuyXGetYStrategy(cart)
        ));
        List<Coupon> applicableCoupons = new ArrayList<Coupon>();
        for (CouponStrategy couponStrategy : couponStrategies) {
            applicableCoupons.addAll(couponStrategy.getApplicableCoupons());
            couponStrategy.calculateDiscount();
        }
        return applicableCoupons;
    }

    public Cart applyCoupon(int couponId, Cart cart) throws Exception {
        Coupon coupon = couponUtil.getCouponById(couponId);
        switch(coupon.getType()) {
            case "cart-wise":
                return new CartWiseStrategy(cart).applyCoupon(coupon);
            case "product-wise":
                return new ProductWiseStrategy(cart).applyCoupon(coupon);
            case "bxgy":
                return new BuyXGetYStrategy(cart).applyCoupon(coupon);
        }
        return null;
    }
}
