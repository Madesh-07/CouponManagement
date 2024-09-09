package com.example.coupon.management;

import com.example.coupon.management.model.Coupon;
import com.fasterxml.jackson.databind.JsonNode;

public class CartRequest {
    private JsonNode cart;

    public CartRequest() {}

    public CartRequest(JsonNode cart) {
        this.cart = cart;
    }

    public JsonNode getCart() {
        return cart;
    }

    public void setCart(JsonNode cart) {
        this.cart = cart;
    }
}