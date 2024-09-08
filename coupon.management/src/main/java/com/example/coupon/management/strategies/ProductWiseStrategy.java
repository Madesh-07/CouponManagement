package com.example.coupon.management.strategies;

import com.example.coupon.management.coupons.ProductWiseCoupon;
import com.example.coupon.management.dao.CouponDAL;
import com.example.coupon.management.enums.coupon.CouponType;
import com.example.coupon.management.model.Cart;
import com.example.coupon.management.model.Coupon;
import com.example.coupon.management.model.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import java.util.*;

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
    public ProductWiseStrategy(Cart cart){
        this.cart = cart;
    }

    @Override
    public List<ProductWiseCoupon> getAllCoupons() throws Exception {
        return List.of();
    }

    @Override
    public List<ProductWiseCoupon> getValidCoupons() throws Exception {
        return null;
        //return (List<ProductWiseCoupon>)CouponDAO.getInstance().findCouponsByExpiryDateGreaterThanEqual(new Date(),"product-wise");
    }

    @Override
    public List<ProductWiseCoupon> getApplicableCoupons() throws Exception {
        List<Product> products = cart.getProducts();
        this.productIdVsTotalProductPrice = constructProductIdVsTotalProductPrice(products,totalCartPrice);
        try{
            this.coupons = couponDAL.getCouponsByQuery(constructQueryForGettingApplicableCoupons(productIdVsTotalProductPrice), ProductWiseCoupon.class);
            return this.coupons;
        }catch (Exception e){
            return null;
        }
    }

    @Override
    public Cart applyCoupon(Coupon coupon) throws Exception {
        ProductWiseCoupon productWiseCoupon = (ProductWiseCoupon) coupon;
        List<Product> products = this.cart.getProducts();
        int couponProductId = productWiseCoupon.getProductId();
        setTotalPriceAndDiscount(couponProductId,products,productWiseCoupon);
        return this.cart;
    }

    private void setTotalPriceAndDiscount(int couponProductId,List<Product> products,ProductWiseCoupon productWiseCoupon) throws Exception{
        for(Product product : products){
            if(product.getProductId() == couponProductId){
                product.setTotalDiscount(product.getPrice() % productWiseCoupon.getDiscountPercentage());
            }
            totalCartPrice = totalCartPrice + (product.getPrice() * product.getQuantity());
        }
        cart.setFinalPrice(cart.getTotalPrice() - cart.getTotalDiscount());
    }

    @Override
    public void calculateDiscount() throws Exception {
        List<ProductWiseCoupon> coupons = this.coupons;
        for(ProductWiseCoupon coupon : coupons){
            if(Objects.nonNull(productIdVsTotalProductPrice.get(coupon.getProductId()))){
                Double discount = productIdVsTotalProductPrice.get(coupon.getProductId()) % coupon.getDiscountPercentage();
                coupon.setDiscount(discount);
            }
        }
    }

    private Map<Integer,Double> constructProductIdVsTotalProductPrice(List<Product> products, Double totalCartPrice){
        Map<Integer,Double> productIdVsTotalProductPrice = new HashMap<Integer,Double>();
        for(Product product : products){
            Double totalProductPrice = product.getPrice() * product.getQuantity();
            productIdVsTotalProductPrice.put(product.getProductId(),totalProductPrice);
            totalCartPrice = totalCartPrice + totalProductPrice;
        }
        return productIdVsTotalProductPrice;
    }

    private Query constructQueryForGettingApplicableCoupons(Map<Integer,Double> productIdVsTotalProductPrice){
        Criteria criteria = Criteria.where("type").is(CouponType.PRODUCT_WISE.getValue());
        criteria = criteria.andOperator(Criteria.where("productId").in(productIdVsTotalProductPrice.keySet()));
        return Query.query(criteria);
    }
}
