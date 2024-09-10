package com.example.coupon.management.strategies;

import com.example.coupon.management.CartWiseCoupon;
import com.example.coupon.management.coupons.BuyXGetYCoupon;
import com.example.coupon.management.coupons.ProductWiseCoupon;
import com.example.coupon.management.dal.CouponDAL;
import com.example.coupon.management.enums.coupon.CouponType;
import com.example.coupon.management.model.Cart;
import com.example.coupon.management.model.Coupon;
import com.example.coupon.management.model.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
@Lazy
public class ProductWiseStrategy implements CouponStrategy {
    CouponDAL couponDAL;
    Cart cart = null;
    List<ProductWiseCoupon> coupons = new ArrayList<>();
    Map<Integer,Double> productIdVsTotalProductPrice = new HashMap<>();
    Double totalCartPrice = 0.0;
    @Autowired
    public ProductWiseStrategy(CouponDAL couponDAL){
        this.couponDAL = couponDAL;
    }

    @Override
    public List<CartWiseCoupon> getAllCoupons() throws Exception {
        Criteria criteria = Criteria.where("type").is(CouponType.PRODUCT_WISE.getValue());
        Query query = Query.query(criteria);
        return couponDAL.getCouponsByQuery(query, BuyXGetYCoupon.class);
    }

    @Override
    public List<CartWiseCoupon> getValidCoupons() throws Exception {
        Criteria criteria = Criteria.where("status").is("true");
        criteria = criteria.andOperator(Criteria.where("type").is(CouponType.PRODUCT_WISE.getValue()));
        Query query = Query.query(criteria);
        return couponDAL.getCouponsByQuery(query, BuyXGetYCoupon.class);
    }

    @Override
    public List<ProductWiseCoupon> getApplicableCoupons(Cart cart) throws Exception {
        this.cart = cart;
        this.totalCartPrice = 0.0;
        List<Product> products = cart.getProducts();
        this.productIdVsTotalProductPrice = constructProductIdVsTotalProductPrice(products,this.totalCartPrice);
        this.coupons = couponDAL.getCouponsByQuery(constructQueryForGettingApplicableCoupons(productIdVsTotalProductPrice), ProductWiseCoupon.class);
        calculateDiscount();
        return this.coupons;
    }

    @Override
    public Cart applyCoupon(Coupon coupon,Cart cart) throws Exception {
        this.cart = cart;
        this.totalCartPrice = 0.0;
        ProductWiseCoupon productWiseCoupon = (ProductWiseCoupon) coupon;
        List<Product> products = this.cart.getProducts();
        int couponProductId = productWiseCoupon.getProductId();
        setTotalPriceAndDiscount(couponProductId,products,productWiseCoupon);
        return this.cart;
    }
    /*
      Setting total discount for the product and the cart's total discount value
     */
    private void setTotalPriceAndDiscount(int couponProductId,List<Product> products,ProductWiseCoupon productWiseCoupon) throws Exception{
        Double totalDiscount = 0.0;
        for(Product product : products){
            if(product.getProductId() == couponProductId){
                Double productDiscount = (product.getPrice() * product.getQuantity()) / productWiseCoupon.getDiscountPercentage();
                product.setTotalDiscount(productDiscount);
                totalDiscount = totalDiscount + productDiscount;
            }
            this.totalCartPrice = this.totalCartPrice + (product.getPrice() * product.getQuantity());
        }
        cart.setTotalDiscount(totalDiscount);
        cart.setTotalPrice(this.totalCartPrice);
        cart.setFinalPrice(cart.getTotalPrice() - cart.getTotalDiscount());
    }

    public void calculateDiscount() throws Exception {
        List<ProductWiseCoupon> coupons = this.coupons;
        for(ProductWiseCoupon coupon : coupons){
            if(Objects.nonNull(productIdVsTotalProductPrice.get(coupon.getProductId()))){
                Double discount = productIdVsTotalProductPrice.get(coupon.getProductId()) / coupon.getDiscountPercentage();
                coupon.setDiscount(discount);
            }
        }
    }
    /*
         constructing productId vs total product price map
     */
    private Map<Integer,Double> constructProductIdVsTotalProductPrice(List<Product> products, Double totalCartPrice){
        Map<Integer,Double> productIdVsTotalProductPrice = new HashMap<Integer,Double>();
        for(Product product : products){
            Double totalProductPrice = product.getPrice() * product.getQuantity();
            productIdVsTotalProductPrice.put(product.getProductId(),totalProductPrice);
            this.totalCartPrice = this.totalCartPrice + totalProductPrice;
        }
        return productIdVsTotalProductPrice;
    }

    private Query constructQueryForGettingApplicableCoupons(Map<Integer,Double> productIdVsTotalProductPrice){
        Criteria criteria = Criteria.where("type").is(CouponType.PRODUCT_WISE.getValue());
        criteria = criteria.andOperator(Criteria.where("productId").in(productIdVsTotalProductPrice.keySet()));
        return Query.query(criteria);
    }
}
