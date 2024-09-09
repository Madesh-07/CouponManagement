package com.example.coupon.management.repository;

import com.example.coupon.management.model.Coupon;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.Update;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface CouponRepository<T extends Coupon> extends MongoRepository<T, String> {
    @Query("{'expiration_date': {$gte: ?0},'type' : ?1}")
    List<T> findCouponsByExpiryDateGreaterThanEqual(Date currentDate,String type);
    @Query("{'coupon_id': ?0}")
    @Update("{'$set': ?1}")
    void updateCoupon(int couponId, Coupon coupon);
}
