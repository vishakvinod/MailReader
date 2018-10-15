/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vis.mail.reader.quartz.jobs;

import com.vis.mail.reader.config.MailReaderJobs;

import org.quartz.Trigger;
import org.quartz.JobDetail;
import org.quartz.JobBuilder;
import org.quartz.TriggerBuilder;
import org.quartz.DailyTimeIntervalScheduleBuilder;
import org.springframework.stereotype.Component;

/**
 *
 * @author Vishak
 */
@Component
public class FiveMinuteMailReaderJob extends MailReaderJobs {

    public FiveMinuteMailReaderJob() {
        super();
    }

    @Override
    public Trigger getTrigger(JobDetail job) {
        DailyTimeIntervalScheduleBuilder dailysch
                = DailyTimeIntervalScheduleBuilder
                        .dailyTimeIntervalSchedule();
        dailysch.withIntervalInMinutes(5);
        return TriggerBuilder.newTrigger().forJob(job).withIdentity(jobName + "tr",
                "FiveMinuteJob").startNow()
                .withSchedule(dailysch).build();
    }

    @Override
    public JobDetail getJob() {
        return (JobDetail) JobBuilder.newJob(FiveMinuteMailReaderJob.class)
                .withIdentity(jobName, "FiveMinuteJob")
                .storeDurably(true).build();
    }
}
