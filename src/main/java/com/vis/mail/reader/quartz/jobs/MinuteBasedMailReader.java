/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vis.mail.reader.quartz.jobs;

import com.vis.mail.reader.config.MailReaderJobs;
import org.quartz.CronScheduleBuilder;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;

/**
 *
 * @author Vishak
 */
public class MinuteBasedMailReader extends MailReaderJobs {

    Integer minuteOfHour;

    public MinuteBasedMailReader() {
    }

    public Integer getMinuteOfHour() {
        return minuteOfHour;
    }

    public void setMinuteOfHour(Integer minuteOfHour) {
        this.minuteOfHour = minuteOfHour;
    }

    @Override
    public Trigger getTrigger(JobDetail job) {
        if (job != null) {
            return TriggerBuilder.newTrigger().forJob(job)
                    .withIdentity(jobName + "tr", "MinuteBasedJob")
                    .startNow()
                    .withSchedule(CronScheduleBuilder
                            .cronSchedule("0 0/" + minuteOfHour + " * 1/1 * ? *"))
                    .build();
        }
        return null;
    }

    @Override
    public JobDetail getJob() {
        if (minuteOfHour != null) {
            return (JobDetail) JobBuilder.newJob(MinuteBasedMailReader.class)
                    .withIdentity(jobName, "MinuteBasedJob")
                    .storeDurably(true).build();
        }
        return null;
    }

}
