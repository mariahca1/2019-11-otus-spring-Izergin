package ru.otus.spring;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

//import org.h2.tools.Console;

import java.sql.SQLException;

//@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
@SpringBootApplication
@EnableMongoRepositories
public class Application {
    public static void main(String[] args) throws SQLException {
        SpringApplication.run(Application.class, args);
//        Console.main(args);


    }
}