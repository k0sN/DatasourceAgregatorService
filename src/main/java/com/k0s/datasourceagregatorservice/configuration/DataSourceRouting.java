package com.k0s.datasourceagregatorservice.configuration;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;


public class DataSourceRouting extends AbstractRoutingDataSource {
    @Override
    protected Object determineCurrentLookupKey() {
        return DataSourceContextHolder.getDataSourceKey();
    }
}
