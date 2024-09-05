package com.example.coupon.management.controller;

import com.example.coupon.management.CouponRequest;
import com.example.coupon.management.model.Coupon;
import com.example.coupon.management.service.coupon.CouponService;
import com.example.coupon.management.util.CouponUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/api/coupons")
public class CouponController {

    @Autowired
    private CouponService couponService;

    @PostMapping
    public ResponseEntity<Coupon> createCoupon(@RequestBody CouponRequest couponRqst) throws Exception{
        Coupon coupon = CouponUtil.validateCouponDetails(couponRqst);
        return ResponseEntity.ok(couponService.addCoupon(coupon));
    }

    @GetMapping
    public ResponseEntity<List<Coupon>> getAllCoupons()  throws Exception{
        return ResponseEntity.ok((List<Coupon>) couponService.getAllCoupons());
    }

//    @GetMapping("/{id}")
//    public ResponseEntity<Coupon> getCouponById(@PathVariable Long id) {
//        return ResponseEntity.ok(couponService.getCouponById(id));
//    }
//
//    @PutMapping("/{id}")
//    public ResponseEntity<Coupon> updateCoupon(@PathVariable Long id, @RequestBody Coupon coupon) {
//        return ResponseEntity.ok(couponService.updateCoupon(id, coupon));
//    }
//
//    @DeleteMapping("/{id}")
//    public ResponseEntity<Void> deleteCoupon(@PathVariable Long id) {
//        couponService.deleteCoupon(id);
//        return ResponseEntity.ok().build();
//    }
}