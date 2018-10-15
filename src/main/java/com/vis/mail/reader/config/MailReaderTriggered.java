/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vis.mail.reader.config;

import java.util.Date;

/**
 *
 * @author Vishak
 */
public interface MailReaderTriggered {
    
    /**
     *
     * @param jobName
     * @param timeStamp
     */
    public void readerTriggered(String jobName, Date timeStamp);
    
}
