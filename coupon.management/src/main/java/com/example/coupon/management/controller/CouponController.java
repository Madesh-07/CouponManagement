package com.example.coupon.management.controller;

import com.example.coupon.management.CartRequest;
import com.example.coupon.management.CouponRequest;
import com.example.coupon.management.enums.common.CommonAPIConstant;
import com.example.coupon.management.enums.coupon.CouponAPIConstant;
import com.example.coupon.management.exceptions.APIException;
import com.example.coupon.management.exceptions.CouponException;
import com.example.coupon.management.model.Cart;
import com.example.coupon.management.model.Coupon;
import com.example.coupon.management.response.APIResponse;
import com.example.coupon.management.service.coupon.CouponService;
import com.example.coupon.management.util.CouponUtil;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Locale;
import org. slf4j.Logger;

@RestController
@RequestMapping("/v1/cms/coupons")
public class CouponController {

    @Autowired
    private CouponService couponService;
    @Autowired
    CouponUtil couponUtil;
    @Autowired
    ObjectMapper objectMapper;
    private static final Logger LOGGER = LoggerFactory.getLogger(CouponController.class);
    @PostMapping
    public APIResponse addCoupon(@RequestBody CouponRequest couponRqst) throws Exception{
        try{
            couponService.addCoupon(couponRqst);
        }catch(CouponException couponException){
            LOGGER.error("Meta Details was not correct.Kindly validate once. Exception {0}",couponException);
            return couponUtil.addResponse(couponException);
        }catch(Exception ex){
            LOGGER.error("Exception occurred while getting coupon. Exception {0}.",ex);
            return couponUtil.addResponse(new APIException(CommonAPIConstant.INTERNAL_SERVER_ERROR));
        }
        LOGGER.info("Coupon added Successfully!");
        return couponUtil.addResponse(CouponAPIConstant.COUPON_CREATED);
    }

    @GetMapping
    public ResponseEntity<List<Coupon>> getAllCoupons(HttpServletRequest request)  throws Exception{
        Locale locale = request.getLocale();
        return ResponseEntity.ok((List<Coupon>) couponService.getAllCoupons(locale));
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getCouponById(@PathVariable int id) throws Exception{
        LOGGER.info("Came to get");
        Coupon coupon = null;
        try {
            coupon = couponService.getCoupon(id);
        } catch(CouponException couponException){
            LOGGER.error("Meta Details was not correct.Kindly validate once. Exception {0}",couponException);
            return ResponseEntity.ok(couponUtil.addResponse(couponException));
        } catch (Exception ex) {
            LOGGER.error("Exception occurred while getting coupon. Exception {0}.",ex);
            ResponseEntity.ok(couponUtil.addResponse(new APIException(CommonAPIConstant.INTERNAL_SERVER_ERROR)));
        }
        return ResponseEntity.ok(coupon);
    }

    @PutMapping("/{couponId}")
    public APIResponse updateCoupon(@PathVariable int couponId,@RequestBody CouponRequest couponRqst) {
        try {
            ResponseEntity.ok(couponService.updateCoupon(couponId, couponRqst));
        }catch(CouponException couponException){
            LOGGER.error("Meta Details was not correct.Kindly validate once. Exception {0}",couponException);
            return couponUtil.addResponse(couponException);
        }catch(Exception ex){
            LOGGER.error("Exception occurred while getting coupon. Exception {0}.",ex);
            return couponUtil.addResponse(new APIException(CommonAPIConstant.INTERNAL_SERVER_ERROR));
        }
        LOGGER.info("Coupon Updated Successfully!.Coupon Id : {0}",couponId);
        return couponUtil.addResponse(CouponAPIConstant.COUPON_UPDATED);
    }

    @DeleteMapping("/{couponId}")
    public  ResponseEntity<?> deleteCoupon(@PathVariable int couponId) {
        try {
            couponService.deleteCoupon(couponId);
        } catch(CouponException couponException){
            LOGGER.error("Meta Details was not correct.Kindly validate once. Exception {0}",couponException);
            return ResponseEntity.ok(couponUtil.addResponse(couponException));
        }catch (Exception ex) {
            LOGGER.error("Exception occurred while getting coupon. Exception {0}.",ex);
            return ResponseEntity.ok(couponUtil.addResponse(new APIException(CommonAPIConstant.INTERNAL_SERVER_ERROR)));
        }
        LOGGER.info("Coupon Deleted Successfully!.Coupon Id : {0}",couponId);
        return  ResponseEntity.ok(couponUtil.addResponse(CouponAPIConstant.COUPON_DELETED));
    }

    @PostMapping("/applicable-coupons")
    public ResponseEntity<?> getApplicableCoupons(@RequestBody CartRequest cartRqst){
        List<Coupon> coupons = null;
        try {
            coupons = couponService.getApplicableCoupons(objectMapper.readValue(cartRqst.getCart().toString(), Cart.class));
        }catch(CouponException couponException){
            LOGGER.error("Meta Details was not correct.Kindly validate once. Exception {0}",couponException);
            return ResponseEntity.ok(couponUtil.addResponse(couponException));
        }catch (Exception ex) {
            LOGGER.error("Exception occurred while getting coupon. Exception {0}.",ex);
            return ResponseEntity.ok(couponUtil.addResponse(new APIException(CommonAPIConstant.INTERNAL_SERVER_ERROR)));
        }
        JsonNode response = couponUtil.constructJSONForApplicableCoupon(coupons);
        return ResponseEntity.ok(response);
    }
    @PostMapping("/apply-coupon/{couponId}")
    public ResponseEntity<?> applyCoupon(@PathVariable int couponId,@RequestBody CartRequest cartRqst) throws Exception{
        Cart cart = null;
        try{
            cart = couponService.applyCoupon(couponId,objectMapper.readValue(cartRqst.getCart().toString(), Cart.class));
        }catch(CouponException couponException){
            LOGGER.error("Meta Details was not correct.Kindly validate once. Exception {0}",couponException);
            return ResponseEntity.ok(couponUtil.addResponse(couponException));
        } catch (Exception ex) {
            LOGGER.error("Exception occurred while getting coupon. Exception {0}.",ex);
            return ResponseEntity.ok(couponUtil.addResponse(new APIException(CommonAPIConstant.INTERNAL_SERVER_ERROR)));
        }
        JsonNode response = couponUtil.constructJSONForApplyCoupon(cart);
        LOGGER.error("Coupon Applied successfully. CouponId : {0}",couponId);
        return ResponseEntity.ok(response);
    }
}