package com.example.coupon.management.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Document("carts")
@Component
public class Cart {
    @Id
    @Field("_cartId")
    @Indexed(unique = true)
    private Long cartId;

    @Field("items")
    @JsonProperty("items")
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
    public void setProducts(List<Product> products) {
        this.products = products;
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