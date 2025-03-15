package com.batch.example.BatchProcessing.steps;

import com.batch.example.BatchProcessing.entity.Product;
import com.batch.example.BatchProcessing.processor.CustomerItemProcessor;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.database.builder.JdbcBatchItemWriterBuilder;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.sql.DataSource;

@Configuration
public class JobSteps {

    @Bean
    public Step steps(JobRepository jobRepository, DataSourceTransactionManager transactionManager,
    FlatFileItemReader<Product> reader, ItemProcessor<Product,Product> itemProcessor, ItemWriter<Product> itemWriter
    ){
        return new StepBuilder("step1", jobRepository)
                .<Product,Product>chunk(2, transactionManager)
                .reader(reader)
                .processor(itemProcessor)
                .writer(itemWriter)
                .allowStartIfComplete(true) // if the job completed successfully first time, then second time this step wont execute, hence we have allowed multiple time run even if its already completed. It will get job information from jobRepository
                .build();
    }

    @Bean
    public FlatFileItemReader<Product> reader(){
        return new FlatFileItemReaderBuilder<Product>()
                .name("ItemReader")
                .resource(new ClassPathResource("data.csv"))
                .linesToSkip(1) // to skip header while reading
                .delimited()
                .names("productId","title","description","price","discount")
                .targetType(Product.class)
                .build();
    }

    @Bean
    public ItemProcessor<Product,Product> processor(){
        return new CustomerItemProcessor();
    }

    @Bean
    public ItemWriter<Product> itemWriter(DataSource dataSource){
        return new JdbcBatchItemWriterBuilder<Product>()
                .sql("insert into products(productId, title, description, price, discount, discounted_price) values(:productId, :title, :description, :price, :discount, :discounted_price)")
                .dataSource(dataSource)
                .beanMapped()
                .build();
    }

}
