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

/**
 * Job run after each minute after startup.
 *
 * @author Vishak
 */
public class OneMinuteMailReaderJob extends MailReaderJobs {

    public OneMinuteMailReaderJob() {
        super();
    }

    @Override
    public Trigger getTrigger(JobDetail job) {
        DailyTimeIntervalScheduleBuilder dailysch
                = DailyTimeIntervalScheduleBuilder
                        .dailyTimeIntervalSchedule();
        dailysch.withIntervalInMinutes(1);
        return TriggerBuilder.newTrigger().forJob(job)
                .withIdentity(jobName + "tr", "OneMinuteJob")
                .withSchedule(dailysch).startNow().build();
    }

    @Override
    public JobDetail getJob() {
        return (JobDetail) JobBuilder.newJob(OneMinuteMailReaderJob.class)
                .withIdentity(jobName, "OneMinuteJob")
                .storeDurably(true).build();
    }

}
