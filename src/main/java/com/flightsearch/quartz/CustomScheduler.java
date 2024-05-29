package com.flightsearch.quartz;

import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import static org.quartz.JobBuilder.newJob;
import static org.quartz.SimpleScheduleBuilder.simpleSchedule;
import static org.quartz.TriggerBuilder.newTrigger;

@Configuration
@Profile({"prodMain", "devMain"})
public class CustomScheduler {
    final Logger log = LoggerFactory.getLogger(getClass());

    @Bean
    public Scheduler scheduler() throws SchedulerException {
        this.log.info("-------Scheduler Initialization Start -----------");

        SchedulerFactory sf = new StdSchedulerFactory();
        Scheduler sched = sf.getScheduler();
        sched.setJobFactory(new JobFactory());
        log.info("-------Scheduler Initialization Complete -----------");

        return sched;
    }

    @Bean
    public JobDetail jobDetail() {
        this.log.info("-------Job instance created -----------");

        return newJob()
                .ofType(CheckSignDeadlineJob.class)
                .storeDurably()
                .withIdentity("job_detail")
                .build();
    }

    @Bean
    public Trigger trigger(JobDetail job) {
        log.info("Configuring trigger to fire every day in 23 pm");

        return newTrigger()
                .forJob(job)
                .withIdentity(TriggerKey.triggerKey("Qrtz_Trigger"))
                .withDescription("Deadline check trigger")
//                .withSchedule(CronScheduleBuilder.dailyAtHourAndMinute(23, 0))
                .withSchedule(simpleSchedule()
                        .withIntervalInSeconds(10)
                        .withRepeatCount(0))
                .build();
    }
}
