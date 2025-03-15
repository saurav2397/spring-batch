package com.batch.example.BatchProcessing.config;

import com.batch.example.BatchProcessing.listener.JobCompletionNotification;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BatchConfig {

    //jobRepository(meta data about job) will get autowired
    @Bean
    public Job jobBean(JobRepository jobRepository, JobCompletionNotification lister, Step steps){
        return new JobBuilder("initialJob",jobRepository)
                .listener(lister)
                .start(steps)
                .build();
    }
}
