package com.example.coupon.management.controller;

import com.example.coupon.management.CouponRequest;
import com.example.coupon.management.coupons.BuyXGetYCoupon;
import com.example.coupon.management.enums.common.CommonAPIConstant;
import com.example.coupon.management.enums.coupon.CouponAPIConstant;
import com.example.coupon.management.exceptions.APIException;
import com.example.coupon.management.model.Cart;
import com.example.coupon.management.model.Coupon;
import com.example.coupon.management.response.APIResponse;
import com.example.coupon.management.service.coupon.CouponService;
import com.example.coupon.management.strategies.BuyXGetYStrategy;
import com.example.coupon.management.util.CouponUtil;
import com.fasterxml.jackson.databind.JsonNode;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Locale;
import org. slf4j.Logger;

@RestController
@RequestMapping("/v1/api/coupons")
public class CouponController {

    @Autowired
    private CouponService couponService;
    @Autowired
    CouponUtil couponUtil;
    @Autowired
    BuyXGetYStrategy b;
    private static final Logger LOGGER = LoggerFactory.getLogger(CouponController.class);
    @PostMapping
    public APIResponse createCoupon(@RequestBody CouponRequest couponRqst) throws Exception{
        try{
            couponService.addCoupon(couponRqst);
        }
        catch(Exception ex){
            return couponUtil.addResponse(new APIException(CommonAPIConstant.INTERNAL_SERVER_ERROR));
        }
        return couponUtil.addResponse(CouponAPIConstant.COUPON_CREATED);
    }

    @GetMapping
    public ResponseEntity<List<Coupon>> getAllCoupons(HttpServletRequest request)  throws Exception{
        Locale locale = request.getLocale();
        return ResponseEntity.ok((List<Coupon>) couponService.getAllCoupons());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Coupon> getCouponById(@PathVariable int id) throws Exception{
        LOGGER.info("Came to get");
        Coupon coupon = null;
        try {
            coupon = couponService.getCoupon(id);
        } catch (Exception e) {
            ResponseEntity.ok(couponUtil.addResponse(new APIException(CommonAPIConstant.INTERNAL_SERVER_ERROR)));
        }
        return ResponseEntity.ok(coupon);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Coupon> updateCoupon(@RequestBody CouponRequest couponRqst) {
        //return ResponseEntity.ok(couponService.updateCoupon(id, coupon));
        return null;
    }

    @DeleteMapping("/{id}")
    public  ResponseEntity<?> deleteCoupon(@PathVariable int id) {
        try {
            couponService.deleteCoupon(id);
        }catch (Exception e) {
            return ResponseEntity.ok(couponUtil.addResponse(new APIException(CommonAPIConstant.INTERNAL_SERVER_ERROR)));
        }
        return  ResponseEntity.ok(couponUtil.addResponse(CouponAPIConstant.COUPON_DELETED));
    }

    @GetMapping("/applicable-coupons")
    public ResponseEntity<?> getApplicableCoupons(@RequestBody Cart cart){
        List<Coupon> coupons = null;
        try {
            coupons = couponService.getApplicableCoupons(cart);
        }catch (Exception e) {
            return ResponseEntity.ok(couponUtil.addResponse(new APIException(CommonAPIConstant.INTERNAL_SERVER_ERROR)));
        }
        JsonNode response = couponUtil.constructJSONForApplicableCoupon(coupons);
        return ResponseEntity.ok(response);
    }
    @PostMapping("/apply-coupon/{id}")
    public ResponseEntity<?> applyCoupon(@PathVariable int couponId,Cart cart) throws Exception{
        try{
            cart = couponService.applyCoupon(couponId,cart);
        } catch (Exception e) {
            return ResponseEntity.ok(couponUtil.addResponse(new APIException(CommonAPIConstant.INTERNAL_SERVER_ERROR)));
        }
        JsonNode response = couponUtil.constructJSONForApplyCoupon(cart);
        return ResponseEntity.ok(response);
    }
}