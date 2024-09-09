package com.example.coupon.management;

import com.example.coupon.management.model.Cart;
import com.example.coupon.management.model.Coupon;
import com.example.coupon.management.service.coupon.CouponService;
import com.example.coupon.management.service.coupon.CouponServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.junit.Assert;

import java.time.LocalDate;
import java.util.List;
import java.util.Locale;

public class CouponTest {
    CouponService couponService;
    @Before
    public void setup() {
        couponService = new CouponServiceImpl(); // Assuming CouponServiceImpl is the implementation of CouponService
    }
    //CRUD Test cases.
    @Test
    public void testAddCartWiseCoupon() throws Exception{
        Coupon coupon = new com.example.coupon.management.CartWiseCoupon(100,"10% offer on cart total above 100",LocalDate.now().plusMonths(1),10,"COUP100");
        coupon = couponService.addCoupon(constructCouponRequestFromCoupon(coupon));
        Assert.assertNotNull(coupon.getCouponId());
    }
    @Test
    public void testGetAllCoupons() throws Exception{
        List<Coupon> coupons = couponService.getAllCoupons(new Locale("en_US"));
        Assert.assertTrue(!coupons.isEmpty());
    }
    //I had taken CartWiseCoupon for update and delete
    @Test
    public void testUpdateCoupon() throws Exception{
        Coupon coupon = new com.example.coupon.management.CartWiseCoupon(100,"20% offer on cart total above 100",LocalDate.now().plusMonths(1),10,"COUP200");
        coupon.setCouponId(1);
        coupon = couponService.updateCoupon(coupon.getCouponId(),constructCouponRequestFromCoupon(coupon));
        Assert.assertEquals(coupon.getCode(),"COUP200");
    }
    @Test
    public void testDeleteCoupon() throws Exception{
        //Assuming we had coupon with id 1.
        couponService.deleteCoupon(1);
    }
    //Other cases.
    @Test
    public void testApplicableCoupons() throws Exception{
        Cart cart = getCartForApplicableAndApplyCoupon();
        List<Coupon> coupon = couponService.getApplicableCoupons(cart);
        //We need to have some applicable coupons based on this cart
        Assert.assertTrue(!coupon.isEmpty());
    }
    @Test
    public void testApplyCoupon() throws Exception{
        int couponId = 1;
        Cart cart = getCartForApplicableAndApplyCoupon();
        Cart resultantCart = couponService.applyCoupon(1,cart);
        Assert.assertEquals(resultantCart.getTotalPrice(),440);
        Assert.assertEquals(resultantCart.getTotalDiscount(),50);
        Assert.assertEquals(resultantCart.getFinalPrice(),390);
    }
    private Cart getCartForApplicableAndApplyCoupon() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        Cart cart = mapper.readValue(constructJSONForApplicableCoupons(mapper).toString(), Cart.class);
        return cart;
    }

    private ObjectNode constructJSONForApplicableCoupons(ObjectMapper mapper) throws Exception{
        ObjectNode cart = mapper.createObjectNode();
        ArrayNode items = mapper.createArrayNode();

        ObjectNode item1 = items.addObject();
        item1.put("product_id", 1);
        item1.put("quantity", 6);
        item1.put("price", 50);
        items.add(item1);

        ObjectNode item2 = items.addObject();
        item2.put("product_id", 2);
        item2.put("quantity", 3);
        item2.put("price", 30);
        items.add(item2);

        ObjectNode item3 = items.addObject();
        item3.put("product_id", 3);
        item3.put("quantity", 2);
        item3.put("price", 25);
        items.add(item3);
        cart.set("items",items);
        return cart;
    }
    private CouponRequest constructCouponRequestFromCoupon(Coupon coupon){
        ObjectMapper mapper = new ObjectMapper();
        CouponRequest couponRequest = new CouponRequest();
        couponRequest.setType(coupon.getType());
        couponRequest.setDetails(mapper.valueToTree(coupon));
        return couponRequest;
    }
}
