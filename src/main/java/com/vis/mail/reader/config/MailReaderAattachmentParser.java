/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vis.mail.reader.config;

import java.util.List;
import com.vis.mail.bean.MailReaderMessage;

/**
 *
 * @author Vishak
 */
public interface MailReaderAattachmentParser {

    /**
     *
     * @param listOfMessages
     * @return
     */
    public boolean parseAttachemnt(List<MailReaderMessage> listOfMessages);

}
