package ru.otus.spring.config;

import com.mongodb.client.MongoClient;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.batch.core.*;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.data.MongoItemReader;
import org.springframework.batch.item.data.MongoItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.LineMapper;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import ru.otus.spring.domain.Account;

import java.time.LocalDateTime;

@Configuration
@EnableBatchProcessing
@RequiredArgsConstructor
public class JobConfiguration {

    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;
    private final MongoClient mongoClient;

    @Bean
    public MongoItemReader<Account> reader() {
        MongoItemReader<Account> mongoItemReader = new MongoItemReader<>();
        mongoItemReader.setCollection("accounts");
        mongoItemReader.setQuery(new Query().addCriteria(Criteria.where("account_id").gte(995000)).limit(10));
        mongoItemReader.setTemplate(new MongoTemplate(mongoClient, "sample_analytics"));
        mongoItemReader.setName("accountItemReader");
        mongoItemReader.setTargetType(Account.class);
        mongoItemReader.setPageSize(10);
        return mongoItemReader;
    }

    @Bean
    public FlatFileItemReader accountFileReader() {
        return new FlatFileItemReaderBuilder<Account>()
                .name("fileReader")
                .resource(new FileSystemResource("source.txt"))
                .lineMapper(new LineMapper() {
                    @Override
                    public Object mapLine(String line, int lineNumber) throws Exception {
                        return new Account()
                                .setAccountId(lineNumber + 10)
                                .setLimit(2)
                                .setLocalDateTime(LocalDateTime.now().plusHours(3));
                    }
                })
                .build();
    }

    @Bean
    public ItemProcessor<Account, Account> accountProcessor() {
        return new ItemProcessor<Account, Account>() {
            @SneakyThrows
            @Override
            public Account process(Account account) {
                Thread.sleep(300);
                return account.setAccountId(account.getAccountId() + 1).setLocalDateTime(LocalDateTime.now().plusHours(3));
            }
        };
    }

    @SneakyThrows
    @Bean
    public MongoItemWriter<Account> accountWriter() {
        MongoItemWriter<Account> mongoItemWriter = new MongoItemWriter<>();
        mongoItemWriter.setCollection("accounts");
        mongoItemWriter.setTemplate(new MongoTemplate(mongoClient, "qweDB"));
        Thread.sleep(100);
        return mongoItemWriter;
    }

    @Bean
    public Job importUserJob() {
        return jobBuilderFactory.get("importUserJob")
                .listener(new JobExecutionListener() {
                    @Override
                    public void beforeJob(JobExecution jobExecution) {
                        System.out.println("start job");
                    }

                    @Override
                    public void afterJob(JobExecution jobExecution) {
                        System.out.println("end job " + jobExecution.getStatus());
                    }
                })
                .preventRestart()
                .start(copyAccounts())
                .build();
    }


    @Bean
    public Step copyAccounts() {
        return stepBuilderFactory.get("copyAccounts")
                .<Account, Account>chunk(12)
//                .reader(reader()) //выдает записи по одной
                .reader(accountFileReader()) //выдает записи по одной
                .processor(accountProcessor())
                .writer(accountWriter()) //только после наполнения чанка
                .listener(new ChunkListener() {
                    @Override
                    public void beforeChunk(ChunkContext context) {
                        System.out.println("before chunk " + context.toString());
                    }

                    @Override
                    public void afterChunk(ChunkContext context) {
                        System.out.println("after chunk " + context.toString());
                    }

                    @Override
                    public void afterChunkError(ChunkContext context) {
                        System.out.println("error chunk " + context.toString());

                    }
                })
                .build();
    }
}
