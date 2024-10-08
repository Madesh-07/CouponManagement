package com.example.coupon.management.util;

import com.example.coupon.management.CartWiseCoupon;
import com.example.coupon.management.CouponRequest;
import com.example.coupon.management.coupons.BuyXGetYCoupon;
import com.example.coupon.management.coupons.ProductWiseCoupon;
import com.example.coupon.management.dal.CouponDAL;
import com.example.coupon.management.enums.common.APIConstant;
import com.example.coupon.management.enums.coupon.CouponAPIConstant;
import com.example.coupon.management.enums.coupon.CouponType;
import com.example.coupon.management.exceptions.APIException;
import com.example.coupon.management.exceptions.CouponException;
import com.example.coupon.management.model.Cart;
import com.example.coupon.management.model.Coupon;
import com.example.coupon.management.model.Product;
import com.example.coupon.management.response.APIResponse;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

@Component
public class CouponUtil {
    List<String> allowedTypes = List.of(CouponType.CART_WISE.getValue(),
                                        CouponType.PRODUCT_WISE.getValue(),
                                        CouponType.BXGY.getValue());
    ObjectMapper mapper;
    @Autowired
    public CouponUtil(ObjectMapper mapper){
        this.mapper = mapper;
    }

    //Validation methods starts here.Soon it will be moved to separate validators under validators package for each coupon.
    /*
        To check whether the coupon is valid
     */
    public void isValidCoupon(int couponId) throws Exception{
        Coupon coupon = getCouponById(couponId);
        if(coupon == null){
            throw new CouponException(CouponAPIConstant.COUPON_NOT_FOUND);
        }
        LocalDate currentDate = LocalDate.now();
        if(coupon.getExpirationDate().isBefore(currentDate)){
            throw new CouponException(CouponAPIConstant.COUPON_EXPIRED);
        }
    }
    /*
     Validation for create and update any type of available coupon
    */
    @Autowired
    CouponDAL couponDAL;
    public Coupon validateCouponDetailsForCreateOrUpdate(CouponRequest couponRqst) throws Exception{
        String type = couponRqst.getType();
        if(!allowedTypes.contains(type)){
            throw new CouponException(CouponAPIConstant.COUPON_NOT_FOUND);
        }
        switch (type) {
            case "cart-wise":
                setTypeKV(couponRqst,type);
                CartWiseCoupon cartWiseCoupon = mapper.readValue(couponRqst.getDetails().toString(),CartWiseCoupon.class);
                return cartWiseCoupon;
            case "product-wise":
                setTypeKV(couponRqst,type);
                ProductWiseCoupon productWiseCoupon = mapper.readValue(couponRqst.getDetails().toString(),ProductWiseCoupon.class);
                return  productWiseCoupon;
            case "bxgy":
                setTypeKV(couponRqst,type);
                BuyXGetYCoupon buyXGetYCoupon = mapper.readValue(couponRqst.getDetails().toString(),BuyXGetYCoupon.class);
                return buyXGetYCoupon;
        }
        return null;
    }


    private void setTypeKV(CouponRequest couponRqst,String type){
        ObjectNode details = (ObjectNode) couponRqst.getDetails();
        details.put("type", type);
    }
    /*
       It is used to get coupon by couponId
     */
    public Coupon getCouponById(int couponId){
        return couponDAL.getCouponById(couponId);
    }
    public String getCouponTypeById(int couponId){
        Coupon coupon =  couponDAL.getCouponById(couponId);
        return coupon.getType();
    }
    public APIResponse addResponse(APIException apiException){
        return addResponse(apiException,null);
    }
    public APIResponse addResponse(APIConstant apiConstant){
        return addResponse(null,apiConstant);
    }
    public APIResponse addResponse(APIException apiException, APIConstant apiConstant){
        if(Objects.nonNull(apiException)){
            return new APIResponse(apiException);
        }
        return new APIResponse(apiConstant);
    }
    /*
        constructing JSON for applicable coupon case
     */
    public JsonNode constructJSONForApplicableCoupon(List<Coupon> coupons){
        ObjectNode responseJson = mapper.createObjectNode();
        ArrayNode couponJsons = mapper.createArrayNode();
        for(Coupon coupon : coupons){
            ObjectNode couponJson = mapper.createObjectNode();
            couponJson.put("coupon_id",coupon.getCouponId());
            couponJson.put("type",coupon.getType());
            couponJson.put("discount",coupon.getDiscount());
            couponJsons.add(couponJson);
        }
        responseJson.set("applicable_coupons",couponJsons);
        return responseJson;
    } /*
        constructing JSON for apply coupon case
     */
    public JsonNode constructJSONForApplyCoupon(Cart cart) throws Exception{
        ObjectNode responseJson = mapper.createObjectNode();
        ObjectNode innerJson = mapper.createObjectNode();
        innerJson.set("items",constructItemsJSON(cart.getProducts()));
        innerJson.put("total_price",cart.getTotalPrice());
        innerJson.put("total_discount",cart.getTotalDiscount());
        innerJson.put("final_price",cart.getFinalPrice());
        responseJson.set("updated_cart",innerJson);
        return responseJson;
    }
    private ArrayNode constructItemsJSON(List<Product> products) throws Exception{
        ArrayNode productJsons = mapper.createArrayNode();
        for(Product product : products){
            ObjectNode productJson = mapper.createObjectNode();
            productJson.put("product_id", product.getProductId());
            productJson.put("quantity", product.getQuantity());
            productJson.put("price", product.getPrice());
            productJson.put("total_discount", product.getTotalDiscount());
            productJsons.add(productJson);
        }
        return productJsons;
    }
}
