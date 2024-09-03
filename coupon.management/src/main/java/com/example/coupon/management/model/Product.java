package com.example.coupon.management.model;

public class Product {
    private Long productId;
    private String name;
    private double price;
    private double discount; // Product-wise discount
    private int quantity;

    public Product(Long productId, String name, double price) {
        this.productId = productId;
        this.name = name;
        this.price = price;
        this.discount = 0.0; // Initialize discount to 0
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