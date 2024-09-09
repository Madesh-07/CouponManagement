CouponManagement
Coupon Management System Design:

 <img width="1332" alt="Screenshot 2024-09-09 at 6 21 29 PM" src="https://github.com/user-attachments/assets/a8a40af8-8708-46c5-bf5c-8447b4b88d0b">

**Tech Stack Used**

  1)Spring Boot and MongoDB Atlas(I had pushed my Mongo URI in this repo.Kindly use other Mongo Atlas URI for testing.)
    
**Cases Implemented :**

  1)In this design,I had covered all the endpoints which was mentioned in the question doc.Also included versioning of API's.Now we are in V1.
  
  2)Consumer can be able to do CRUD operations on various Coupon.
  
  3)Consumer can be able to apply coupon on specific cart and get the applicable coupons for the provided cart.
    **extra cases implemented** :
      ->I have added loggers(Used log4j2) in specific areas which will be very useful for debugging purpose in large scale systems.Here we can track every operations on Coupons.

**UnImplemented Cases;**

  1)No cases was unimplemented which was mentioned in the doc.
  
  2)I had covered **bonus** requirements as well by implementing expiration date for a coupon using LocalDate class in Java and also written unit test cases for the provided endoints.
    ->Used service layer for writing test cases.
    
**Planned But not Implemented(Not in Question Doc):**

  1)I had planned to implement caching for coupons.Since coupons were frequently used across the system.
  
  2)Also I had planned to implemnent **JWT(JSON Web Token) ** for authorizing API's.
  
  3)Planned to Dockerize these things, so that consumer will be able to run in their machine easily(i.e)without any dependency errors.
  
**Partially Implemented :**

  1)I had tried to provide Internationalization(I18N) support for coupon descriptions, due to time constraint I had able to develop around 60%.For sample case, I had added around for 5       
    Languages.It's     main usage is to provide a more personalized and relevant experience to our customers in different regions of the world.
    
  2)**Validation Layer:**
      ->I had created a separate validation layer for validating coupons.A separate interface and its child classes were created.But due to time constraint, I had provided basic validations.In         future we can provide separate validations for every coupon in their respective validation classes.

**Limitations:**


1)As far I had verified, there is no limitations in Cart wise coupon and Product Wise coupon.But In BuyXGetY coupon, both X and Y should be present in the request payload in buy and get     
    products respectively.
      because,  
          Let's say Y is not present in the request payload cart, since Y is an array(For eg:[A,B,C]), there is  possiblity of every product to be eligible for Y place.This was the limitation in           BuyXGetY coupon.

**Some of my suggestions in Question:**
    
    
  ->In cart wise and product wise coupon POST request payload, we can modify "discount" key to "discount_percentage"  which will be more precise.
  
  2)In BuyXGetYCoupon generating payload, we can modify the structure, which is segregating the buy quantity and get quantity to separate key.So that duplicate key value will be avoided.
  
  Images

  <img width="290" alt="Screenshot 2024-09-09 at 6 40 39 PM" src="https://github.com/user-attachments/assets/12bf617f-6462-4ff1-9dd1-988ea5d525e6">

 
  <img width="166" alt="Screenshot 2024-09-09 at 6 40 42 PM" src="https://github.com/user-attachments/assets/5b43c8f2-c641-48c5-8c71-204813a12296">

    
**Corrections in apply coupon endpoint:**   
    
  
  1)In result, under updated_result.items.product_id{3}.total_discount is 40, but in the overall total_discount it is 50.Also total cart price is 440.But it is mentioned as 300.
  
  
  <img width="664" alt="Screenshot 2024-09-09 at 6 41 52 PM" src="https://github.com/user-attachments/assets/ff15b8da-a049-45cc-9a42-eefbc492872b"> 
  
  
  <img width="350" alt="Screenshot 2024-09-09 at 6 46 08 PM" src="https://github.com/user-attachments/assets/b0509beb-b291-48e9-8294-9a5576c39493">
    
**Scalablity of the system:**

  1)In future, we can include an additional coupon type like other types(BXGY,CartWise,ProductWise) by simply adding it's strategy in strategies package and include a child class of parent class     "Coupon" and its respective validator.
  
    Example :  I am trying to add "First-Time Buyer Coupon". I can include 
    
                          FirstTimeBuyerCoupon.java -> which will be extending Coupon Class
                          
                          FirstTimeBuyerStrategy.java -> which will be implementing CouponStrategy interface.
                          
                          FirstTimeBuyerValidatore.java -> which will be implementing CommonValidator interface.
                          
    **Benefits of this design:**
    
           No coupon specific logics will be clubbed or merged together which makes code un maintainable and un readable.Rather it will be in its own class.So I had chosen strategy pattern for              this scenario.


    




