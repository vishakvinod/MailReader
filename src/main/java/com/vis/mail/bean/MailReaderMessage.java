/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vis.mail.bean;

import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Vishak
 */
public class MailReaderMessage {

    MailReaderClient sender;
    String subject;
    Map<String, InputStream> fileAsInputStream;
    String timestamp;
    SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy hh mm ss");

    public MailReaderMessage(MailReaderClient sender, String subject) {
        this.sender = sender;
        this.subject = subject;
        fileAsInputStream = new HashMap<>();
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public MailReaderClient getSender() {
        return sender;
    }

    public void setSender(MailReaderClient sender) {
        this.sender = sender;
    }

    public Map<String, InputStream> getFiles() {
        return fileAsInputStream;
    }

    public void addFiles(String name, InputStream ip) {
        fileAsInputStream.put(name, ip);
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        //ATTACH DATE FORMATER
        this.timestamp = dateFormat.format(timestamp);
    }

}
