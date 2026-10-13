package com.example.demo;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import software.amazon.awssdk.services.dynamodb.DynamoDbClient;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedClient;
import software.amazon.awssdk.regions.Region;

@Configuration
public class DynamoConfig {

    @Bean
    public DynamoDbClient databaseClient() {
        return DynamoDbClient.builder()
                .region(Region.US_EAST_2)
                .build();   
    }

    @Bean
    public DynamoDbEnhancedClient enhancedDatabaseClient(DynamoDbClient dynamoDbClient) {
        return DynamoDbEnhancedClient.builder()
                .dynamoDbClient(dynamoDbClient)
                .build();
    }
}