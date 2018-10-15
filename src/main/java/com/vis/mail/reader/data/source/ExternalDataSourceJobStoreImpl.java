/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vis.mail.reader.data.source;

import javax.sql.DataSource;

/**
 *
 * @author Vishak
 */
public class ExternalDataSourceJobStoreImpl implements DataStore {

    private final DataSource ds;

    public ExternalDataSourceJobStoreImpl(DataSource ds) {
        this.ds = ds;
    }

    @Override
    public DataSource getDataSource() {
        return ds;
    }

}
