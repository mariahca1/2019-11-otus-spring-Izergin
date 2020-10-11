package ru.otus.spring.config;

//import com.mongodb.MongoClientOptions;
//import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
//import org.springframework.boot.autoconfigure.mongo.MongoClientFactory;
import com.mongodb.client.MongoIterable;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.util.BsonUtils;
//import org.springframework.data.mongodb.core.MongoClientFactoryBean;

@Configuration
public class AppConfig {


    @Bean
    MongoClient mongoClient() {
        MongoClient mongoClient = MongoClients.create("mongodb+srv://user:pass@clusteriis.9alo6.mongodb.net/<dbname>?retryWrites=true&w=majority");
        MongoIterable<String> strings = mongoClient.listDatabaseNames();

        return mongoClient;
//        MongoClientSettings mongoClientSettings;
    }

//    @Bean
//    MongoClientFactoryBean mongoClientFactoryBean() {
//        MongoClientFactoryBean mongo = new MongoClientFactoryBean();
//
//        MongoClientOptions mongoClientOptions;
//        mongoClientOptions.
//        mongo.setHost("localhost");
//        return mongo;
//    }
}
