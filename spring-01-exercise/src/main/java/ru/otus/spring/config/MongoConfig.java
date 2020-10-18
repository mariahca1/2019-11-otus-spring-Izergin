package ru.otus.spring.config;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoIterable;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MongoConfig {
    @Bean
    MongoClient mongoClient() {
        MongoClient mongoClient = MongoClients.create("mongodb+srv://user:pass@clusteriis.9alo6.mongodb.net/<dbname>?retryWrites=true&w=majority");
        MongoIterable<String> strings = mongoClient.listDatabaseNames();
        return mongoClient;
    }
}
