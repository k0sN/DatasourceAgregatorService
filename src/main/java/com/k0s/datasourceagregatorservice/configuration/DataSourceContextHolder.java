package com.k0s.datasourceagregatorservice.configuration;

import lombok.extern.slf4j.Slf4j;
import org.springframework.util.Assert;

@Slf4j
public class DataSourceContextHolder {

    private static final ThreadLocal<String> dataSourceKey = new ThreadLocal<>();

    public static void setDataSourceKey(String key) {
        log.info("Set {} in data source context holder", key);
        Assert.notNull(key, "clientDatabase cannot be null");
        dataSourceKey.set(key);
    }

    public static String getDataSourceKey() {
        return dataSourceKey.get();
    }

    public static void clearDataSourceKey() {
        log.info("Clear data source context holder");
        dataSourceKey.remove();
    }
}
