package com.example.coupon.management.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Document("products")
public class Product{
    @Id
    @Field("product_id")
    private int productId;

    @Field("name")
    @Indexed(unique = true)
    private String name;

    @Field("price")
    private double price;

    @Field("total_discount")
    private double totalDiscount;

    @Field("quantity")
    private int quantity;

    @Field("available_quantity")
    private int available_quantity;

    public Product(){

    }
    public Product(int productId, String name, double price,double totalDiscount) {
        this.productId = productId;
        this.name = name;
        this.price = price;
        this.totalDiscount = totalDiscount; // Initialize discount to 0
    }

    public int getProductId() {
        return productId;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    public double getTotalDiscount() {
        return totalDiscount;
    }

    public void setTotalDiscount(double totalDiscount) {
        this.totalDiscount = totalDiscount;
    }

    public int getQuantity() {
        return quantity;
    }

    public int getAvailable_quantity(){
        return available_quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}