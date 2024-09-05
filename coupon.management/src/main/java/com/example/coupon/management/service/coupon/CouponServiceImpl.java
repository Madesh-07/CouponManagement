package com.example.coupon.management.service.coupon;

import com.example.coupon.management.model.Coupon;
import com.example.coupon.management.repository.CouponRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CouponServiceImpl implements CouponService {

    @Autowired
    CouponRepository couponRepo;

    public Coupon addCoupon(Coupon coupon) throws Exception{
        return couponRepo.save(coupon);
    }
    public Coupon getCoupon(Long couponId) throws Exception{
        return null;
    }
    public Coupon updateCoupon(Coupon coupon) throws Exception{
        return null;
    }
    public List<Coupon> getAllCoupons() throws Exception{
        return null;
    }
    public List<Coupon> getApplicableCoupons(Long cartId) throws Exception{
        return null;
    }
    public List<Coupon> applyCoupons(Long cartId) throws Exception{
        return null;
    }
}
