package com.example.coupon.management.dal;

import com.example.coupon.management.model.Coupon;
import com.example.coupon.management.repository.CouponRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;
import org.springframework.data.mongodb.core.query.Update;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Component
@Lazy
public class CouponDAL<T extends Coupon> {
    private CouponRepository couponRepository;
    MongoTemplate mongoTemplate;

    @Autowired
    public CouponDAL(CouponRepository couponRepository, MongoTemplate mongoTemplate) {
        this.couponRepository = couponRepository;
        this.mongoTemplate = mongoTemplate;
    }


    public T insertCoupon(T coupon){
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
    public Coupon getCouponById(int couponId){
        Criteria criteria = Criteria.where("coupon_id").is(couponId);
        return mongoTemplate.findOne(Query.query(criteria),Coupon.class);
    }
    public List<T> getCouponsByQuery(Query query,Class<T> couponClass) throws Exception{
        return mongoTemplate.find(query,couponClass);
    }
    public Coupon updateSpecificCoupon(Coupon coupon) throws Exception {
        couponRepository.updateCoupon(coupon.getCouponId(),coupon);
        return coupon;
    }

    public void deleteCouponById(int couponId){
        Criteria criteria = Criteria.where("coupon_id").is(couponId);
        mongoTemplate.remove(Query.query(criteria),Coupon.class);
    }

}
