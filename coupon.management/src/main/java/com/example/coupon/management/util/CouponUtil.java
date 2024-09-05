package com.example.coupon.management.util;

import com.example.coupon.management.CartWiseCoupon;
import com.example.coupon.management.CouponRequest;
import com.example.coupon.management.coupons.BuyXGetYCoupon;
import com.example.coupon.management.coupons.ProductWiseCoupon;
import com.example.coupon.management.enums.coupon.CouponErrorDetail;
import com.example.coupon.management.enums.coupon.CouponType;
import com.example.coupon.management.exceptions.CouponException;
import com.example.coupon.management.model.Coupon;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.EnumSet;
import java.util.List;

public class CouponUtil {
    static List<String> allowedTypes = List.of(CouponType.CART_WISE.getValue(), CouponType.PRODUCT_WISE.getValue(), CouponType.BXGY.getValue());
    public static Coupon validateCouponDetails(CouponRequest couponRqst) throws Exception{
        String type = couponRqst.getType();
        if(!allowedTypes.contains(type)){
            throw new CouponException(CouponErrorDetail.COUPON_NOT_FOUND);
        }
        switch (type) {
            case "cart-wise":
                return (CartWiseCoupon)couponRqst.getDetails();
            case "product-wise":
                return  (ProductWiseCoupon)couponRqst.getDetails();
            case "bxgy":
                return (BuyXGetYCoupon)couponRqst.getDetails();
        }
        return null;
    }
}
