/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vis.mail.bean;

import com.vis.mail.reader.config.MailReaderClientExtractor;
import com.vis.mail.reader.config.MailReaderAattachmentParser;
import com.vis.mail.reader.config.MailReaderTriggered;

/**
 *
 * @author Vishak
 */
public class MailReaderJobData {

    private MailReaderDetails readerDetails = new MailReaderDetails();
    private boolean sendResponse = false;

    private MailReaderClientExtractor clientListExtractor = null;
    private MailReaderAattachmentParser attachmentExtractor = null;
    private MailReaderTriggered mailReaderTriggered = null;

    public String getEmailId() {
        return readerDetails.getEmailId();
    }

    public void setEmailId(String emailId) {
        readerDetails.setEmailId(emailId);
    }

    public String getPassword() {
        return readerDetails.getPassword();
    }

    public void setPassword(String password) {
        readerDetails.setPassword(password);
    }

    public boolean isSendResponse() {
        return sendResponse;
    }

    public void setSendResponse(boolean sendResponse) {
        this.sendResponse = sendResponse;
    }

    public MailReaderClientExtractor getClientListExtractor() {
        return clientListExtractor;
    }

    public void setClientListExtractor(
            MailReaderClientExtractor clientListExtractor) {
        this.clientListExtractor = clientListExtractor;
    }

    public MailReaderAattachmentParser getAttachmentExtractor() {
        return attachmentExtractor;
    }

    public void setAttachmentExtractor(
            MailReaderAattachmentParser attachmentExtractor) {
        this.attachmentExtractor = attachmentExtractor;
    }

    public MailReaderTriggered getMailReaderTriggered() {
        return mailReaderTriggered;
    }

    public void setMailReaderTriggered(
            MailReaderTriggered mailReaderTriggered) {
        this.mailReaderTriggered = mailReaderTriggered;
    }

}
