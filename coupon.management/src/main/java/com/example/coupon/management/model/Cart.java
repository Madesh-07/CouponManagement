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
    private List<Product> products;
    private double total;
    private final String ADD = "Add";
    private final String REMOVE = "Remove";
    public Cart() {
        this.products = new ArrayList<>();
    }
    public Long getCartId() {
        return cartId;
    }

    public void setCartId(Long cartId) {
        this.cartId = cartId;
    }

    public void addProduct(Product product, int quantity) {
        product.setQuantity(quantity);
        products.add(product);
        updateTotal(product.getPrice(),ADD);
    }

    public void removeProduct(Product product) {
        products.remove(product);
        updateTotal(product.getPrice(),REMOVE);
    }

    public List<Product> getProducts() {
        return products;
    }

    private void updateTotal(double price,String operation) {
        if(operation.equals(ADD)) {
            this.total = this.total + price;
        }else if (operation.equals(REMOVE)){
            this.total = this.total - price;
        }
    }

    public double getTotal() {
        return total;
    }
}