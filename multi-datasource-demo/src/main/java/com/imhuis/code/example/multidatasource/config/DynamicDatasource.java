package com.imhuis.code.example.multidatasource.config;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

/**
 * @author: imhuis
 * @date: 2021/12/22
 * @description:
 */
public class DynamicDatasource extends AbstractRoutingDataSource {

    @Override
    protected Object determineCurrentLookupKey() {
        return DatasourceHolder.getDataSource();
    }
}
