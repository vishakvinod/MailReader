package com.vis.mail;

import javax.sql.DataSource;

import com.vis.mail.reader.data.source.DataStore;
import com.vis.mail.reader.config.ScheduleBuilder;
import com.vis.mail.reader.data.source.ExternalDataSourceJobStoreImpl;
import com.vis.mail.reader.config.quartz.jobstore.MailReaderRAMStoreBeansInitializer;
import com.vis.mail.reader.config.quartz.jobstore.MailReaderJDBCStoreBeansInitializer;

import org.quartz.SchedulerException;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * Initialize Spring beans and the Quartz Scheduler and add all the jobs to the
 * scheduler.
 *
 * @author Vishak
 * @since 30-09-2018
 */
public class MailReaderInitializer {

    public static AnnotationConfigApplicationContext ctx
            = new AnnotationConfigApplicationContext();

    public static DataStore dataStore = null;

    private ScheduleBuilder scheduleBuilder = null;

    /**
     * Constructor with data source for jdbc store based quartz.
     */
    public MailReaderInitializer(DataSource ds, 
            boolean startScheduler) throws SchedulerException {
        //JDBC STORE BASED QUARTZ.
        dataStore = new ExternalDataSourceJobStoreImpl(ds);
        ctx.register(MailReaderJDBCStoreBeansInitializer.class);
        ctx.refresh();
        scheduleBuilder
                = (ScheduleBuilder) ctx.getBean("scheduleBuilder");        
    }

    /**
     * Constructor with data source for RAM store based quartz.
     */
    public MailReaderInitializer(boolean startScheduler) 
            throws SchedulerException {
        //RAM STORE BASED QUARTZ.
        ctx.register(MailReaderRAMStoreBeansInitializer.class);
        ctx.refresh();
        scheduleBuilder
                = (ScheduleBuilder) ctx.getBean("scheduleBuilder");
    }

    public DataStore getDataStore() {
        return dataStore;
    }

    public ScheduleBuilder getScheduleBuilder() {
        return scheduleBuilder;
    }
}
