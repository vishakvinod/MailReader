/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vis.mail.reader.utils;

import javax.mail.Address;

/**
 *
 * @author Vishak
 */
public class MailReaderUtils {

    private static String getFrom(Address a[]) {
        String from = "";
        if (a == null) {
            return null;
        }
        for (int i = 0; i < a.length; i++) {
            Address address = a[i];
            from = from + address.toString();
        }
        return from;
    }
}
