package com.example.coupon.management.dao;

import com.example.coupon.management.enums.coupon.CouponType;
import com.example.coupon.management.model.Coupon;
import com.example.coupon.management.repository.CouponRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class CouponDAL<T extends Coupon> {

    private final CouponRepository couponRepository;

    @Autowired
    public CouponDAL(CouponRepository couponRepository) {
        this.couponRepository = couponRepository;
    }

    @Autowired
    MongoTemplate mongoTemplate;

    public T addCoupon(T coupon){
        return (T) couponRepository.save(coupon);
    }
    public List<T> findCouponsByExpiryDateGreaterThanEqual(Date date,String type){
        return couponRepository.findCouponsByExpiryDateGreaterThanEqual(date,type);
    }

    public List<T> getAllCoupons(){
        return (List<T>)couponRepository.findAll()
                .stream()
                .collect(Collectors.toList());
    }
    public T getCouponById(int couponId){
        return (T) couponRepository.findById(couponId).orElse(null);
    }
    public List<T> getCouponsByQuery(Query query,Class<T> couponClass) throws Exception{
        return mongoTemplate.find(query,couponClass);
    }

    public void deleteCouponById(int couponId){
         couponRepository.deleteById(couponId);
    }

}
