package com.flightsearch.quartz;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.slf4j.Logger;

@Component
public class CheckSignDeadlineJob implements Job {
    Logger logger = LoggerFactory.getLogger(getClass());

    public void execute(JobExecutionContext context) {
        logger.info("Started execution of CheckSignDeadlineJob with context = {}", context);


        logger.info("Finished execution of CheckSignDeadlineJob");
    }
}
