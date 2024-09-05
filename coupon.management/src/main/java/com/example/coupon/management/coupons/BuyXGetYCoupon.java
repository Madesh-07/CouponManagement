package com.example.coupon.management.coupons;

import com.example.coupon.management.enums.coupon.CouponType;
import com.example.coupon.management.model.Coupon;
import com.example.coupon.management.model.Product;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;
import java.util.List;

@Document("buy_x_get_y_coupon")
public class BuyXGetYCoupon extends Coupon {
    private List<Product> buyProducts;
    private List<Product> getProducts;
    private int repetitionLimit;
    private int buyQuantity;
    private int getQuantity;

    public BuyXGetYCoupon(Long couponId, List<Product> buyProducts, List<Product> getProducts, int buyQuantity, int getQuantity, String description, LocalDate expirationDate, int repetitionLimit) {
        super(couponId, CouponType.BXGY.getValue(), description, expirationDate);
        this.buyProducts = buyProducts;
        this.getProducts = getProducts;
        this.buyQuantity = buyQuantity;
        this.getQuantity = getQuantity;
        this.repetitionLimit = repetitionLimit;
    }
    public List<Product> getGetProducts() {
        return getProducts;
    }

    public List<Product> getBuyProducts() {
        return buyProducts;
    }

    public int getRepetitionLimit() {
        return repetitionLimit;
    }

    public int getBuyQuantity() {
        return buyQuantity;
    }

    public int getGetQuantity() {
        return getQuantity;
    }
}