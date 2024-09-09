package com.example.coupon.management.strategies;

import com.example.coupon.management.CartWiseCoupon;
import com.example.coupon.management.coupons.BuyXGetYCoupon;
import com.example.coupon.management.dal.CouponDAL;
import com.example.coupon.management.enums.coupon.CouponAPIConstant;
import com.example.coupon.management.enums.coupon.CouponType;
import com.example.coupon.management.exceptions.CouponException;
import com.example.coupon.management.model.Cart;
import com.example.coupon.management.model.Coupon;
import com.example.coupon.management.model.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class CartWiseStrategy implements CouponStrategy{
    CouponDAL couponDAL;
    Cart cart = null;
    List<CartWiseCoupon> coupons = new ArrayList<>();
    Double totalCartPrice = 0.0;

    @Autowired
    public CartWiseStrategy(CouponDAL couponDAL){
        this.couponDAL = couponDAL;
    }

    @Override
    public List<CartWiseCoupon> getAllCoupons() throws Exception {
        Criteria criteria = Criteria.where("type").is(CouponType.CART_WISE.getValue());
        Query query = Query.query(criteria);
        return couponDAL.getCouponsByQuery(query, BuyXGetYCoupon.class);
    }

    @Override
    public List<CartWiseCoupon> getValidCoupons() throws Exception {
        Criteria criteria = Criteria.where("status").is("true");
        criteria = criteria.andOperator(Criteria.where("type").is(CouponType.CART_WISE.getValue()));
        Query query = Query.query(criteria);
        return couponDAL.getCouponsByQuery(query, BuyXGetYCoupon.class);
    }

    @Override
    public List<? extends Coupon> getApplicableCoupons(Cart cart) throws Exception {
        this.cart = cart;
        this.totalCartPrice = 0.0;
        List<Product> products = this.cart.getProducts();
        processTotalCartPrice(products,this.totalCartPrice);
        try{
            this.coupons = couponDAL.getCouponsByQuery(constructQueryForGettingApplicableCoupons(totalCartPrice), CartWiseCoupon.class);
            calculateDiscount();
            return this.coupons;
        }catch (Exception e){
            return null;
        }
    }
    /*
         To calculate discount for the cart w.r.t cart wise coupon
      */
    public void calculateDiscount() throws Exception {
       List<CartWiseCoupon> coupons = this.coupons;
       for(CartWiseCoupon coupon : coupons){
           Double discount = totalCartPrice / coupon.getDiscountPercentage();
           coupon.setDiscount(discount);
       }
    }
    /*
        To process the total cart price
     */
    private void processTotalCartPrice(List<Product> products,Double totalCartPrice){
        for(Product product : products){
            Double totalProductPrice = product.getPrice() * product.getQuantity();
            this.totalCartPrice = this.totalCartPrice + totalProductPrice;
        }
    }
    /*
       Constructing query for applicable coupons
     */
    private Query constructQueryForGettingApplicableCoupons(Double totalCartPrice) throws Exception{
        Criteria criteria = Criteria.where("type").is(CouponType.CART_WISE.getValue());
        criteria = criteria.andOperator(Criteria.where("threshold").lt(totalCartPrice));
        return Query.query(criteria);
    }

    @Override
    public Cart applyCoupon(Coupon coupon,Cart cart) throws Exception {
        this.cart = cart;
        this.totalCartPrice = 0.0;
        CartWiseCoupon cartCoupon = (CartWiseCoupon)coupon;
        List<Product> products = cart.getProducts();
        Double totalDiscount = 0.0;
        calculateCartTotalPrice(products);
        if(this.totalCartPrice < cartCoupon.getThreshold()){
            throw new CouponException(CouponAPIConstant.COUPON_NOT_APPLICABLE);
        }
        applyProductWiseDiscountAndCalculateTotalDiscount(totalDiscount,cartCoupon.getDiscountPercentage(),products);
        return this.cart;
    }
    private void calculateCartTotalPrice(List<Product> products){
        for(Product product : products){
            this.totalCartPrice = this.totalCartPrice + (product.getQuantity() * product.getPrice());
        }
    }
    /*
     To apply product wise discount in the cart
    */
    private void applyProductWiseDiscountAndCalculateTotalDiscount(Double totalDiscount,Double percentage,List<Product> products){
        for(Product product : products){
            Double discount = (product.getPrice() * product.getQuantity()) / percentage;
            product.setTotalDiscount(discount);
            totalDiscount = totalDiscount + discount;
        }
        cart.setTotalDiscount(totalDiscount);
        cart.setTotalPrice(this.totalCartPrice);
        cart.setFinalPrice(this.totalCartPrice - totalDiscount);
    }
}
