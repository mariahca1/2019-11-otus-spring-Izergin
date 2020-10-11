package ru.otus.spring.controller;

import com.mongodb.client.MongoClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import ru.otus.spring.domain.Account;

import java.time.LocalDateTime;
import java.util.List;

@ShellComponent
public class SpringBatchApi {

    @Autowired
    MongoClient mongoClient;

    @ShellMethod(key = {"bstart", "b"}, value = "Start processing data")
    public String start() {
        System.out.println("ok");

//mongoClient = MongoClients.create("mongodb+srv://user:pass@clusteriis.9alo6.mongodb.net/<dbname>?retryWrites=true&w=majority");
        MongoOperations destDb = new MongoTemplate(mongoClient, "qweDB");
//        destDb.findAll(Account.class).stream().map((q) -> destDb.remove(q));
//        destDb.insert(new Person("q",1).setLocalDateTime(LocalDateTime.now()));


        MongoOperations sourceDb = new MongoTemplate(mongoClient, "sample_analytics");
        List<Account> accounts = sourceDb.findAll(Account.class);
        System.out.println(accounts.size());

        Account account = accounts.get(0);
        account.setLocalDateTime(LocalDateTime.now());

        destDb.insert(account);


        return String.format("Hello, %s. Now you can start an exam!", "studentName");

//        DataSource


    }
}
