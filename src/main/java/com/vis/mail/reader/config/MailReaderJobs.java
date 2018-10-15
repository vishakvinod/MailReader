/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vis.mail.reader.config;

import java.util.List;
import java.util.ArrayList;
import java.util.Properties;

import javax.mail.Part;
import javax.mail.Store;
import javax.mail.Folder;
import javax.mail.Session;
import javax.mail.Message;
import javax.mail.Transport;
import javax.mail.Multipart;
import javax.mail.Flags.Flag;
import javax.mail.search.FromTerm;
import javax.mail.search.SearchTerm;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeBodyPart;
import javax.mail.NoSuchProviderException;
import javax.mail.internet.InternetAddress;

import org.quartz.JobKey;
import org.quartz.Trigger;
import org.quartz.JobDetail;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.vis.mail.bean.MailReaderClient;
import com.vis.mail.bean.MailReaderJobData;
import com.vis.mail.bean.MailReaderMessage;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.QuartzJobBean;

/**
 *
 * @author Vishak
 */
public abstract class MailReaderJobs extends QuartzJobBean {

    private static final Logger Logger = LoggerFactory
            .getLogger(MailReaderJobs.class);

    @Autowired
    ScheduleBuilder scheduleBuilder;

    protected String jobName;

    MailReaderJobData data;

    Properties properties = null;

    private Session session = null;
    private Store store = null;
    private Folder inbox = null;

    public MailReaderJobs() {
        initProperties();
        data = new MailReaderJobData();
    }

    private void initProperties() {
        properties = new Properties();
        properties.setProperty("mail.host", "imap.gmail.com");
        properties.setProperty("mail.port", "993");
        properties.setProperty("mail.transport.protocol", "imaps");
    }

    public abstract Trigger getTrigger(JobDetail job);

    public abstract JobDetail getJob();

    public void sendResponse(boolean sendResponse) {
        data.setSendResponse(sendResponse);
    }

    /**
     *
     * @param jec
     * @throws JobExecutionException
     */
    @Override
    public void executeInternal(JobExecutionContext jec)
            throws JobExecutionException {
        JobKey key = jec.getJobDetail().getKey();
        String jname = key.getName();
        Date dt = jec.getFireTime();
        if (data.getMailReaderTriggered() != null) {
            data.getMailReaderTriggered().readerTriggered(jname, dt);
        }
        session = Session.getDefaultInstance(properties, null);
        try {
            System.out.println("Job Name : " + jname);
            data = scheduleBuilder.getJobData(jname);
            store = session.getStore("imaps");
            store.connect(getEmailId(), getPassword());
            inbox = store.getFolder("INBOX");
            inbox.open(Folder.READ_WRITE);
            List<MailReaderMessage> fileAsInputStream = new ArrayList<>();
            List<MailReaderClient> clientAddressList
                    = data.getClientListExtractor().getListOfClients();

            if (clientAddressList != null && !clientAddressList.isEmpty()) {
                for (MailReaderClient clientAddress : clientAddressList) {
                    SearchTerm sender
                            = new FromTerm(
                                    new InternetAddress(
                                            clientAddress.getEmailId()));
                    Message[] messages = inbox.search(sender);
                    for (Message message : messages) {
                        if (!message.isSet(Flag.SEEN)) {
                            if (message.getContent() instanceof Multipart) {
                                MailReaderMessage mrMessage
                                        = new MailReaderMessage(
                                                clientAddress,
                                                message.getSubject());
                                mrMessage.setTimestamp(
                                        message.getSentDate());
                                Multipart multiPart
                                        = ((Multipart) message.getContent());
                                int parts = multiPart.getCount();
                                for (int partCount = 0; partCount < parts;
                                        partCount++) {
                                    MimeBodyPart part
                                            = (MimeBodyPart) multiPart
                                                    .getBodyPart(partCount);
                                    if (Part.ATTACHMENT.equalsIgnoreCase(
                                            part.getDisposition())) {
                                        String fileName = part.getFileName();
                                        mrMessage.addFiles(fileName,
                                                part.getInputStream());
                                    }
                                }
                                if (data.isSendResponse()) {
                                    sendAcknowledgement(message);
                                }
                                fileAsInputStream.add(mrMessage);
                            }
                            message.setFlag(Flag.SEEN, true);
                        }
                    }
                }
            }
            if (!fileAsInputStream.isEmpty()) {
                data.getAttachmentExtractor()
                        .parseAttachemnt(fileAsInputStream);
            }
            inbox.close(true);
            store.close();
        } catch (NoSuchProviderException e) {
            Logger.error("Error while running the job", e);
        } catch (MessagingException e) {
            Logger.error("Error while running the job", e);
        } catch (Throwable e) {
            Logger.error("Error while running the job", e);
        }

    }

    /**
     *
     * @param message
     * @throws MessagingException
     */
    public void sendAcknowledgement(Message message) throws MessagingException {
        Message replyMessage = new MimeMessage(session);
        replyMessage = (MimeMessage) message.reply(false);
        String to = InternetAddress.toString(message
                .getRecipients(Message.RecipientType.TO));
        replyMessage.setFrom(new InternetAddress(to));
        replyMessage.setText("This is an autogenerated mail.<br>"
                + "The attachment has been processed by auto mail reader.<br> "
                + "Please verify the data in the portal.");
        replyMessage.setReplyTo(message.getReplyTo());
        Transport t = session.getTransport("smtp");
        try {
            t.connect(getEmailId(), getPassword());
            t.sendMessage(replyMessage,
                    replyMessage.getAllRecipients());
        } finally {
            t.close();
        }
    }

    /**
     *
     * @param clientListExtractor
     */
    public void setClientListExtractor(
            MailReaderClientExtractor clientListExtractor) {
        data.setClientListExtractor(clientListExtractor);
    }

    /**
     *
     * @param attachmentExtractor
     */
    public void setAttachmentExtractor(
            MailReaderAattachmentParser attachmentExtractor) {
        data.setAttachmentExtractor(attachmentExtractor);
    }

    /**
     *
     * @param readerTrigger
     */
    public void setOnTrigger(
            MailReaderTriggered readerTrigger) {
        data.setMailReaderTriggered(readerTrigger);
    }

    /**
     *
     * @return
     */
    public MailReaderJobData getData() {
        return data;
    }

    /**
     *
     * @return
     */
    public ScheduleBuilder getScheduleBuilder() {
        return scheduleBuilder;
    }

    /**
     *
     * @param scheduleBuilder
     */
    public void setScheduleBuilder(ScheduleBuilder scheduleBuilder) {
        this.scheduleBuilder = scheduleBuilder;
    }

    /**
     *
     * @return
     */
    public String getJobName() {
        return jobName;
    }

    /**
     *
     * @param jobName
     */
    public void setJobName(String jobName) {
        this.jobName = jobName;
    }

    /**
     *
     * @return
     */
    public String getEmailId() {
        return data.getEmailId();
    }

    /**
     *
     * @param emailId
     */
    public void setEmailId(String emailId) {
        data.setEmailId(emailId);
    }

    /**
     *
     * @return
     */
    public String getPassword() {
        return data.getPassword();
    }

    /**
     *
     * @param password
     */
    public void setPassword(String password) {
        data.setPassword(password);
    }
}
