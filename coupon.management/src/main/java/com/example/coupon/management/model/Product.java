package com.example.coupon.management.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Document("products")
public class Product {
    @Id
    @Field("_productId")
    private Long productId;

    @Field("name")
    @Indexed(unique = true)
    private String name;

    @Field("price")
    private double price;

    @Field("discount")
    private double discount;

    @Field("quantity")
    private int quantity;

    public Product(Long productId, String name, double price,double discount) {
        this.productId = productId;
        this.name = name;
        this.price = price;
        this.discount = discount; // Initialize discount to 0
    }

    public Long getProductId() {
        return productId;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    public double getDiscount() {
        return discount;
    }

    public void setDiscount(double discount) {
        this.discount = discount;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}