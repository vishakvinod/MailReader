/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vis.mail.reader.config;

import java.util.Map;
import java.util.HashMap;

import org.quartz.Scheduler;
import org.quartz.SchedulerException;

import com.vis.mail.bean.MailReaderJobData;

import java.util.Set;
import java.util.HashSet;

import org.quartz.Trigger;
import org.quartz.JobDetail;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.scheduling.quartz.SchedulerFactoryBean;

/**
 *
 * @author Vishak
 */
public class ScheduleBuilder {

    private static final Logger Logger = LoggerFactory
            .getLogger(ScheduleBuilder.class);

    SchedulerFactoryBean schedulerFactoryBean;
    Map<String, MailReaderJobData> jobData = new HashMap<>();

    private ScheduleBuilder() {
    }

    /**
     * Constructor for Schedule builder. This 
     *
     * @param schedulerFactoryBean
     * @param startScheduler
     * @throws SchedulerException
     */
    public ScheduleBuilder(SchedulerFactoryBean schedulerFactoryBean,
            boolean startScheduler) throws SchedulerException {
        this.schedulerFactoryBean = schedulerFactoryBean;
        if (startScheduler) {
            schedulerFactoryBean.start();
        }
        Logger.info("SchdeuleBuilder :: Inititalized.... [OK]");
    }

    /**
     *
     * @param jobBean
     * @throws SchedulerException
     */
    public void addSchedule(MailReaderJobs jobBean)
            throws SchedulerException {
        Scheduler schedule = schedulerFactoryBean.getScheduler();
        JobDetail job = jobBean.getJob();
        Trigger trigger = jobBean.getTrigger(job);

        schedule.addJob(job, true);

        schedule.rescheduleJob(trigger.getKey(), trigger);
        Set<Trigger> triggers = new HashSet<>();
        triggers.add(trigger);

        Map<JobDetail, Set<? extends Trigger>> triggersAndJobs
                = new HashMap<JobDetail, Set<? extends Trigger>>();
        triggersAndJobs.put(job, triggers);
        schedule.scheduleJobs(triggersAndJobs, true);

        Logger.info(job.getKey().getName() + " schedule added");
        jobData.put(job.getKey().getName(), jobBean.getData());
    }

    /**
     *
     * @param waitForJobsToComplete
     * @throws SchedulerException
     */
    public void shutdownSchedule(boolean waitForJobsToComplete)
            throws SchedulerException {
        schedulerFactoryBean.getScheduler().shutdown(waitForJobsToComplete);
    }

    /**
     *
     * @param jobName
     * @return
     */
    public MailReaderJobData getJobData(String jobName) {
        return jobData.get(jobName);
    }
}
