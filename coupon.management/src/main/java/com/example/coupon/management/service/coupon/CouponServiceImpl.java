package com.example.coupon.management.service.coupon;

import com.example.coupon.management.CouponRequest;
import com.example.coupon.management.controller.CouponController;
import com.example.coupon.management.dal.CouponDAL;
import com.example.coupon.management.enums.coupon.CouponAPIConstant;
import com.example.coupon.management.exceptions.CouponException;
import com.example.coupon.management.model.Cart;
import com.example.coupon.management.model.Coupon;
import com.example.coupon.management.sequence.NextSequenceService;
import com.example.coupon.management.strategies.CouponStrategy;
import com.example.coupon.management.util.CouponUtil;
import com.example.coupon.management.util.TranslationUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;

@Service
public class CouponServiceImpl implements CouponService {
    private static final Logger LOGGER = LoggerFactory.getLogger(CouponServiceImpl.class);
    @Autowired
    NextSequenceService nextGenService;
    @Autowired
    CouponDAL couponDAL;
    @Autowired
    CouponUtil couponUtil;
    @Autowired
    private Map<String, CouponStrategy> couponStrategies;

    /*
         To add coupons
     */
    public Coupon addCoupon(CouponRequest couponRqst) throws Exception {
        Coupon coupon = null;
        try {
            coupon = couponUtil.validateCouponDetailsForCreateOrUpdate(couponRqst);
            coupon.setCouponId(nextGenService.getNextSequence("customSequences"));
        } catch (CouponException coupExc) {
            throw coupExc;
        }
        return couponDAL.insertCoupon(coupon);
    }

    public Coupon getCoupon(int couponId) throws Exception {
        couponUtil.isValidCoupon(couponId);
        return couponDAL.getCouponById(couponId);
    }
    /*
         To update specific coupon
    */
    public Coupon updateCoupon(int couponId, CouponRequest couponRqst) throws Exception {
        couponUtil.isValidCoupon(couponId);
        Coupon coupon = null;
        coupon = couponUtil.validateCouponDetailsForCreateOrUpdate(couponRqst);
        coupon.setCouponId(couponId);
        return couponDAL.updateSpecificCoupon(coupon);
    }
    /*
       To delete coupons
    */
    @Override
    public boolean deleteCoupon(int couponId) throws Exception {
        couponUtil.isValidCoupon(couponId);
        couponDAL.deleteCouponById(couponId);
        return true;
    }
    /*
        To get all coupons
     */
    public List<Coupon> getAllCoupons(Locale locale) throws Exception {
        List<Coupon> coupons =  couponDAL.getAllCoupons();
        for(Coupon coupon : coupons){
            try {
                coupon.setDescription(TranslationUtil.getTranslatedMessage(coupon.getDescription(), locale));
            }catch(Exception ex){
                LOGGER.info("Exception occurred while getting translated vale for coupon description {0}",ex);
            }
        }
        if(coupons.isEmpty()){
            throw new CouponException(CouponAPIConstant.NO_COUPONS_FOUND);
        }
        return coupons;
    }

    /*
       To get Applicable coupons of particular cart
     */
    public List<Coupon> getApplicableCoupons(Cart cart) throws Exception {
        List<Coupon> applicableCoupons = new ArrayList<>();
        for (CouponStrategy couponStrategy : this.couponStrategies.values()) {
            applicableCoupons.addAll(couponStrategy.getApplicableCoupons(cart));
        }
        if(applicableCoupons.isEmpty()){
            throw new CouponException(CouponAPIConstant.NO_APPLICABLE_COUPONS);
        }
        return applicableCoupons;
    }
    /*
       To apply specific coupon to specific cart
     */
    public Cart applyCoupon(int couponId, Cart cart) throws Exception {
        couponUtil.isValidCoupon(couponId);
        Coupon coupon = couponUtil.getCouponById(couponId);
        switch (coupon.getType()) {
            case "cart-wise":
                return this.couponStrategies.get("cartWiseStrategy").applyCoupon(coupon, cart);
            case "product-wise":
                return this.couponStrategies.get("productWiseStrategy").applyCoupon(coupon, cart);
            case "bxgy":
                return this.couponStrategies.get("buyXGetYStrategy").applyCoupon(coupon, cart);
        }
        return null;
    }
}
