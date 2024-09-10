package com.example.coupon.management.strategies;

import com.example.coupon.management.coupons.BuyXGetYCoupon;
import com.example.coupon.management.dal.CouponDAL;
import com.example.coupon.management.enums.coupon.CouponAPIConstant;
import com.example.coupon.management.enums.coupon.CouponType;
import com.example.coupon.management.exceptions.APIException;
import com.example.coupon.management.exceptions.CouponException;
import com.example.coupon.management.model.Cart;
import com.example.coupon.management.model.Coupon;
import com.example.coupon.management.model.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;

@Component
@Lazy
public class BuyXGetYStrategy implements CouponStrategy {

    @Autowired
    CouponDAL couponDAL;
    Cart cart = null;
    List<BuyXGetYCoupon> coupons = new ArrayList<>();
    Map<Integer,Integer> productIdVQuantity = new HashMap<>();
    Double totalCartPrice = 0.0;
    @Autowired
    public BuyXGetYStrategy(CouponDAL couponDAL){
        this.couponDAL = couponDAL;
    }
    @Override
    public List<BuyXGetYCoupon> getAllCoupons() throws Exception {
        Criteria criteria = Criteria.where("type").is(CouponType.BXGY.getValue());
        Query query = Query.query(criteria);
        return couponDAL.getCouponsByQuery(query,BuyXGetYCoupon.class);
    }

    /*
       To get valid coupons
    */
    @Override
    public List<BuyXGetYCoupon> getValidCoupons() throws Exception {
        Criteria criteria = Criteria.where("status").is("true");
        criteria = criteria.andOperator(Criteria.where("type").is(CouponType.BXGY.getValue()));
        Query query = Query.query(criteria);
        return couponDAL.getCouponsByQuery(query, BuyXGetYCoupon.class);
    }

    /*
       To get all applicable coupons
    */
    @Override
    public List<? extends Coupon> getApplicableCoupons(Cart cart) throws Exception {
        this.cart = cart;
        this.totalCartPrice = 0.0;
        List<Product> products = this.cart.getProducts();
        this.productIdVQuantity = constructProductIdVsQuantity(products,totalCartPrice);
        this.coupons = couponDAL.getCouponsByQuery(constructQueryForGettingApplicableCoupons(productIdVQuantity), BuyXGetYCoupon.class);
        filterCoupons();
        calculateDiscount();
        return coupons;
    }
    /*
        To filter out the BUYXGETY coupons.
     */
    private void filterCoupons() throws Exception {
        List<BuyXGetYCoupon> buyXgetYFilteredCoupons = new ArrayList<>();
        coupons.stream().forEach(bxgyCoupon -> {
            List<Integer> getProductIds = bxgyCoupon.getGetProducts().stream()
                    .map(Product::getProductId) // assuming Product has an getId method
                    .collect(Collectors.toList());
            if (getProductIds.stream().anyMatch(productIdVQuantity::containsKey)) {
                buyXgetYFilteredCoupons.add(bxgyCoupon);
            }
        });
        this.coupons = buyXgetYFilteredCoupons;
    }
    /*
         To calculate discount for the cart w.r.t BXGY coupon
     */
    public void calculateDiscount() throws Exception {
        List<BuyXGetYCoupon> coupons = this.coupons;
        coupons.stream()
                .forEach(coupon -> {
                    coupon.getBuyProducts().stream()
                            .forEach(product -> {
                                int productId = product.getProductId();
                                if (productIdVQuantity.containsKey(productId)) {
                                    int quantity = productIdVQuantity.get(productId);
                                    if(quantity >= coupon.getBuyQuantity()){
                                        Double discount = calculateDiscountIfGetProductsPresent(coupon.getGetProducts(),coupon);
                                        coupon.setDiscount(discount);
                                    }
                                }
                            });
                });
    }
    /*
        To calculate discount for the cart w.r.t BXGY coupon if only get products is present
    */
    private Double calculateDiscountIfGetProductsPresent(List<Product> getProducts,BuyXGetYCoupon coupon){
        return getProducts.stream()
                .filter(product -> Objects.nonNull(productIdVQuantity.get(product.getProductId())))
                .mapToDouble(product -> {
                    if(Objects.nonNull(productIdVQuantity.get(product.getProductId()))){
                        return coupon.getGetQuantity() * getProductPriceById(product.getProductId());
                    } else {
                        return productIdVQuantity.get(product.getProductId()) * getProductPriceById(product.getProductId());
                    }
                })
                .sum();
    }
    /*
        To get product price by its id
    */
    private double getProductPriceById(int productId){
        Cart cart = this.cart;
        return cart.getProducts().stream()
                .filter(product -> product.getProductId() == productId)
                .findFirst()
                .map(Product::getPrice)
                .orElse(0.0);
    }
    /*
       Constructing util for product vs quantity
    */
    private Map<Integer,Integer> constructProductIdVsQuantity(List<Product> products, Double totalCartPrice){
        Map<Integer,Integer> productIdVsQuantity = new HashMap<Integer,Integer>();
        for(Product product : products){
            Double totalProductPrice = product.getPrice() * product.getQuantity();
            productIdVsQuantity.put(product.getProductId(),product.getQuantity());
            this.totalCartPrice = this.totalCartPrice + totalProductPrice;
        }
        return productIdVsQuantity;
    }
    /*
         Constructing query for applicable BXGY coupons
     */
    private Query constructQueryForGettingApplicableCoupons(Map<Integer,Integer> productIdVQuantity){
        Criteria criteria = Criteria.where("type").is(CouponType.BXGY.getValue());
        criteria = criteria.andOperator(Criteria.where("buy_products.product_id").in(productIdVQuantity.keySet()));
        return Query.query(criteria);
    }

    @Override
    public Cart applyCoupon(Coupon coupon,Cart cart) throws Exception {
        BuyXGetYCoupon bxgyCoupon = (BuyXGetYCoupon)coupon;
        if(bxgyCoupon.getRepetitionLimit() == 0){
            throw new CouponException(CouponAPIConstant.COUPON_LIMIT_REACHED);
        }
        this.cart = cart;
        this.totalCartPrice = 0.0;
        List<Product> products = this.cart.getProducts();
        List<Integer> buyProductIds = bxgyCoupon.getBuyProducts().stream()
                .map(Product::getProductId) // assuming Product has an getId method
                .collect(Collectors.toList());
        List<Integer> getProductIds = bxgyCoupon.getGetProducts().stream()
                .map(Product::getProductId) // assuming Product has an getId method
                .collect(Collectors.toList());
        this.productIdVQuantity = constructProductIdVsQuantity(products,this.totalCartPrice);
        return constructCartDetails(bxgyCoupon,products,buyProductIds,getProductIds);
    }
    private Cart constructCartDetails(BuyXGetYCoupon bxgyCoupon, List<Product> products, List<Integer> buyProductIds, List<Integer> getProductIds) throws Exception {
        productIdVQuantity.entrySet().stream()
                .filter(entry -> buyProductIds.contains(entry.getKey()) && bxgyCoupon.getBuyQuantity() >= entry.getValue())
                .forEach(entry -> modifyProductDetailsInCart(bxgyCoupon, products, getProductIds));

        int totalDiscount = products.stream()
                .map(Product::getTotalDiscount)
                .mapToInt(Double::intValue)
                .sum();
        cart.setTotalDiscount(totalDiscount);
        cart.setTotalPrice(this.totalCartPrice);
        cart.setFinalPrice(this.totalCartPrice - totalDiscount);
        //Once user applied this current coupon, we should reduce the limit.
        updateRepetionLimitValue(bxgyCoupon);
        return cart;
    }
    /*
       Updating repetition value if user applies BXGY coupon
    */
    private void updateRepetionLimitValue(BuyXGetYCoupon bxgyCoupon) throws Exception{
        int repitionLimit = bxgyCoupon.getRepetitionLimit();
        bxgyCoupon.setRepetitionLimit(repitionLimit -1);
        couponDAL.updateSpecificCoupon(bxgyCoupon);
    }

    private void modifyProductDetailsInCart(BuyXGetYCoupon bxgyCoupon, List<Product> products, List<Integer> getProductIds) {
        getProductIds.stream()
                .filter(productIdVQuantity::containsKey)
                .forEach(id -> {
                    int getCartProductQuantity = productIdVQuantity.get(id);
                    Product product = products.stream()
                            .filter(p -> p.getProductId() == id)
                            .findFirst()
                            .orElseThrow();

                    double price = product.getPrice();
                    double totalDiscount = price * getCartProductQuantity;
                    price = price / getCartProductQuantity;
                    getCartProductQuantity = bxgyCoupon.getGetQuantity() + getCartProductQuantity;

                    product.setPrice(totalDiscount/getCartProductQuantity);
                    product.setTotalDiscount(totalDiscount);
                    product.setQuantity(getCartProductQuantity);
                });
    }
}
