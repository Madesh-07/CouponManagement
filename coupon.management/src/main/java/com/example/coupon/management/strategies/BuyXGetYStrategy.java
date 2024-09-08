package com.example.coupon.management.strategies;

import com.example.coupon.management.coupons.BuyXGetYCoupon;
import com.example.coupon.management.dao.CouponDAL;
import com.example.coupon.management.enums.coupon.CouponType;
import com.example.coupon.management.model.Cart;
import com.example.coupon.management.model.Coupon;
import com.example.coupon.management.model.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class BuyXGetYStrategy implements CouponStrategy {

    CouponDAL couponDAL;
    Cart cart = null;
    List<BuyXGetYCoupon> coupons = new ArrayList<>();
    Map<Integer,Integer> productIdVQuantity = new HashMap<>();
    @Autowired
    public BuyXGetYStrategy(CouponDAL couponDAL){
        this.couponDAL = couponDAL;
    }
    public BuyXGetYStrategy(Cart cart){
        this.cart = cart;
    }
    @Override
    public List<BuyXGetYCoupon> getAllCoupons() throws Exception {
        Criteria criteria = Criteria.where("type").is(CouponType.BXGY.getValue());
        Query query = Query.query(criteria);
        return couponDAL.getCouponsByQuery(query,BuyXGetYCoupon.class);
    }

    @Override
    public List<BuyXGetYCoupon> getValidCoupons() throws Exception {
        return List.of();
    }

    @Override
    public List<? extends Coupon> getApplicableCoupons() throws Exception {
        List<Product> products = this.cart.getProducts();
        Double totalCartPrice = 0.0;
        this.productIdVQuantity = constructProductIdVsQuantity(products,totalCartPrice);
        try{
            this.coupons = couponDAL.getCouponsByQuery(constructQueryForGettingApplicableCoupons(productIdVQuantity), BuyXGetYCoupon.class);
            return coupons;
        }catch (Exception e){
            return null;
        }
    }

    @Override
    public void calculateDiscount() throws Exception {
        List<BuyXGetYCoupon> coupons = this.coupons;
        coupons.stream()
                .forEach(coupon -> {
                    coupon.getBuyProducts().stream()
                            .forEach(product -> {
                                int productId = product.getProductId();
                                if (productIdVQuantity.containsKey(productId)) {
                                    int quantity = productIdVQuantity.get(productId);
                                    if(quantity >= product.getQuantity()){
                                        Double discount = calculateDiscountIfGetProductsPresent(coupon.getGetProducts());
                                        coupon.setDiscount(discount);
                                    }
                                }
                            });
                });
    }
    private Double calculateDiscountIfGetProductsPresent(List<Product> products){
        return products.stream()
                .filter(product -> Objects.nonNull(productIdVQuantity.get(product.getProductId())))
                .mapToDouble(product -> {
                    if(productIdVQuantity.get(product.getProductId()) > product.getQuantity()){
                        return product.getQuantity() * getProductPriceById(product.getProductId());
                    } else {
                        return productIdVQuantity.get(product.getProductId()) * getProductPriceById(product.getProductId());
                    }
                })
                .sum();
    }

    private double getProductPriceById(int productId){
        Cart cart = this.cart;
        return cart.getProducts().stream()
                .filter(product -> product.getProductId() == productId)
                .findFirst()
                .map(Product::getPrice)
                .orElse(0.0);
    }
    private Map<Integer,Integer> constructProductIdVsQuantity(List<Product> products, Double totalCartPrice){
        Map<Integer,Integer> productIdVsQuantity = new HashMap<Integer,Integer>();
        for(Product product : products){
            Double totalProductPrice = product.getPrice() * product.getQuantity();
            productIdVsQuantity.put(product.getProductId(),product.getQuantity());
            totalCartPrice = totalCartPrice + totalProductPrice;
        }
        return productIdVsQuantity;
    }

    private Query constructQueryForGettingApplicableCoupons(Map<Integer,Integer> productIdVQuantity){
        Criteria criteria = Criteria.where("type").is(CouponType.BXGY.getValue());
        criteria = criteria.andOperator(Criteria.where("buyProducts.productId").in(productIdVQuantity.keySet()));
        return Query.query(criteria);
    }


    @Override
    public Cart applyCoupon(Coupon coupon) throws Exception {
        return null;
    }
}
