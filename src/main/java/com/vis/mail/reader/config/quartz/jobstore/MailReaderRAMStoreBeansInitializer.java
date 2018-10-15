/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vis.mail.reader.config.quartz.jobstore;

import org.quartz.spi.JobFactory;

import com.vis.mail.MailReaderInitializer;
import com.vis.mail.reader.config.ScheduleBuilder;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;

/**
 *
 * @author Vishak
 */
@Configuration
public class MailReaderRAMStoreBeansInitializer {

    @Bean(name = "quartzJobFactory")
    public JobFactory getAutowiringSpringBeanJobFactory() {
        AutowiringSpringBeanJobFactory autowiringSpringBeanJobFactory
                = new AutowiringSpringBeanJobFactory();
        autowiringSpringBeanJobFactory.setApplicationContext(
                MailReaderInitializer.ctx);
        return autowiringSpringBeanJobFactory;
    }

    @Bean(name = "schedulerFactoryBean")
    public SchedulerFactoryBean initSchedulerFactoryBean() throws Exception {
        SchedulerFactoryBean factory = new SchedulerFactoryBean();
        factory.setOverwriteExistingJobs(true);
        factory.setAutoStartup(true);
        factory.setJobFactory(getAutowiringSpringBeanJobFactory());
        factory.setConfigLocation(
                new ClassPathResource("RamStore/quartz.properties"));
        return factory;
    }

    @Bean(name = "scheduleBuilder")
    public ScheduleBuilder initScheduleBuilder() throws Exception {
        return new ScheduleBuilder(
                initSchedulerFactoryBean(), true);
    }

}
