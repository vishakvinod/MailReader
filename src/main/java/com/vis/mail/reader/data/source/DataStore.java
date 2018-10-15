package com.vis.mail.reader.data.source;

import javax.sql.DataSource;

/**
 *
 * @author Vishak
 */
public interface DataStore {

    public abstract DataSource getDataSource();
}
