/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vis.mail.reader.config;

import com.vis.mail.bean.MailReaderClient;
import java.util.List;

/**
 *
 * @author Vishak
 */
public interface MailReaderClientExtractor {
    
    /**
     *
     * @return
     */
    public List<MailReaderClient> getListOfClients();
}
