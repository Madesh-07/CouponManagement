package com.example.coupon.management.repository;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoClientDatabaseFactory;

@Configuration
public class MongoDBConfig {

    @Value("${spring.data.mongodb.uri}")
    String connectionStringURI;
    @Bean(name = "mongoTemplate")
    public MongoTemplate mongoTemplate() {
        MongoClient mongoClient = MongoClients.create(connectionStringURI);
        return new MongoTemplate(new SimpleMongoClientDatabaseFactory(mongoClient, "coupon_management"));
    }
}