/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vis.mail.reader.config.quartz.jobstore;

import com.vis.mail.MailReaderInitializer;
import com.vis.mail.reader.config.ScheduleBuilder;

import org.quartz.spi.JobFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.ClassPathResource;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;

/**
 *
 * @author Vishak
 */
@Configuration
public class MailReaderJDBCStoreBeansInitializer {

    @Bean(name = "quartzJobFactory")
    public JobFactory getAutowiringSpringBeanJobFactory() {
        return new AutowiringSpringBeanJobFactory();
    }

    @Bean(name = "scheduleFactory")
    public SchedulerFactoryBean initSchedulerFactoryBean() {
        SchedulerFactoryBean factory = new SchedulerFactoryBean();
        factory.setConfigLocation(
                new ClassPathResource("jdbcStore/quartz.properties"));
        factory.setDataSource(MailReaderInitializer.dataStore.getDataSource());
        factory.setWaitForJobsToCompleteOnShutdown(true);
        factory.setJobFactory(getAutowiringSpringBeanJobFactory());
        return factory;
    }

    @Bean(name = "scheduleBuilder")
    public ScheduleBuilder initScheduleBuilder() throws Exception {
        return new ScheduleBuilder(
                initSchedulerFactoryBean(), true);
    }
}
