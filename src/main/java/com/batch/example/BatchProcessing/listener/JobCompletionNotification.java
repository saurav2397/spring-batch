package com.batch.example.BatchProcessing.listener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.stereotype.Component;

@Component
public class JobCompletionNotification implements JobExecutionListener {

    private Logger logger= LoggerFactory.getLogger(JobCompletionNotification.class);

    @Override
    public void beforeJob(JobExecution jobExecution) {
        logger.info("Job started");
    }

    @Override
    public void afterJob(JobExecution jobExecution) {

        if(jobExecution.getStatus() == BatchStatus.COMPLETED){
            logger.info("Job ended OK");
        }else {
            logger.error("Job ended NOT OK");
        }

    }
}
