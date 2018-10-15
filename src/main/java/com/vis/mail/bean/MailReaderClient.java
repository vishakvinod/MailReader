/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vis.mail.bean;

/**
 *
 * @author Vishak
 */
public class MailReaderClient {

    String name;
    String emailId;

    public MailReaderClient(String name, String emailId) {
        this.name = name;
        this.emailId = emailId;
    }   

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmailId() {
        return emailId;
    }

    public void setEmailId(String emailId) {
        this.emailId = emailId;
    }

    
}
