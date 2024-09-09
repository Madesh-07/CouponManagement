package com.example.coupon.management.coupons;

import com.example.coupon.management.enums.coupon.CouponType;
import com.example.coupon.management.model.Coupon;
import com.example.coupon.management.model.Product;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.time.LocalDate;
import java.util.List;

public class BuyXGetYCoupon extends Coupon {
    @JsonProperty("buy_products")
    @Field("buy_products")
    private List<Product> buyProducts;
    @JsonProperty("get_products")
    @Field("get_products")
    private List<Product> getProducts;
    @JsonProperty("repetition_limit")
    @Field("repetition_limit")
    private int repetitionLimit;
    @JsonProperty("buy_quantity")
    @Field("buy_quantity")
    private int buyQuantity;
    @JsonProperty("get_quantity")
    @Field("get_quantity")
    private int getQuantity;

    public BuyXGetYCoupon(List<Product> buyProducts, List<Product> getProducts, int buyQuantity, int getQuantity, String description, LocalDate expirationDate, int repetitionLimit,String code) {
        super(CouponType.BXGY.getValue(), description, expirationDate,code);
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
    public void setRepetitionLimit(int repetitionLimit) {
        this.repetitionLimit = repetitionLimit;
    }

    public int getBuyQuantity() {
        return buyQuantity;
    }

    public int getGetQuantity() {
        return getQuantity;
    }
}