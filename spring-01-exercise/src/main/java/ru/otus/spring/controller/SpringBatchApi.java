package ru.otus.spring.controller;

import com.mongodb.client.MongoClient;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.launch.JobOperator;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import ru.otus.spring.domain.Account;

@ShellComponent
@RequiredArgsConstructor
public class SpringBatchApi {

    private final MongoClient mongoClient;
    private final Job importUserJob;
    private final JobLauncher jobLauncher;
    private final JobOperator jobOperator;

    //http://localhost:8080/h2-console/

    @SneakyThrows
    @ShellMethod(key = {"startBatchProcessing", "b"}, value = "Start processing data")
    public String start() {
        System.out.println("ok");

//mongoClient = MongoClients.create("mongodb+srv://user:pass@clusteriis.9alo6.mongodb.net/<dbname>?retryWrites=true&w=majority");
        MongoOperations destDb = new MongoTemplate(mongoClient, "qweDB");
        destDb.remove(Account.class).all();


//        JobExecution jobExecution = jobLauncher.run(importUserJob, new JobParametersBuilder()
//                .toJobParameters());
        jobOperator.start("importUserJob","");

        return String.format("Hello, %s. Now you can start an exam!", "studentName");

//        DataSource


    }
}
