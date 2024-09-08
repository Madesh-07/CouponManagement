package com.example.coupon.management.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.ArrayList;
import java.util.List;

@Document("carts")
public class Cart {
    @Id
    @Field("_cartId")
    @Indexed(unique = true)
    private Long cartId;
    @Field("items")
    private List<Product> products;
    @Field("total_price")
    private double totalPrice;

    @Field("total_discount")
    private double totalDiscount;
    @Field("final_price")
    private double finalPrice;

    public Cart() {
        this.products = new ArrayList<>();
    }
    public Long getCartId() {
        return cartId;
    }

    public void setCartId(Long cartId) {
        this.cartId = cartId;
    }


    public List<Product> getProducts() {
        return products;
    }


    public double getTotal() {
        return totalPrice;
    }

    public double getTotalDiscount() {
        return totalDiscount;
    }

    public double getFinalPrice() {
        return finalPrice;
    }
    public void setTotalDiscount(double totalDiscount) {
        this.totalDiscount = totalDiscount;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public void setFinalPrice(double finalPrice) {
        this.finalPrice = finalPrice;
    }
}