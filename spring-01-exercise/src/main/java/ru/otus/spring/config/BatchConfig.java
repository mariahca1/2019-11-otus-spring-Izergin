package ru.otus.spring.config;

import org.springframework.batch.core.configuration.JobRegistry;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.support.JobRegistryBeanPostProcessor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@EnableBatchProcessing
@Configuration
public class BatchConfig {

    //регистрируем бины джобов чтобы их можно было запускать через jobOperator
    @Bean
    public JobRegistryBeanPostProcessor postProcessor(JobRegistry jobRegistry) {
        JobRegistryBeanPostProcessor processor = new JobRegistryBeanPostProcessor();
        processor.setJobRegistry(jobRegistry);
        return processor;
    }

}
