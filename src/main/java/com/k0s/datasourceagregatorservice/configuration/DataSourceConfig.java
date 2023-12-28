package com.k0s.datasourceagregatorservice.configuration;

import com.k0s.datasourceagregatorservice.configuration.properties.DataSourceProperties;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

@Configuration
@RequiredArgsConstructor
public class DataSourceConfig {


    public static final int MAXIMUM_POOL_SIZE = Runtime.getRuntime().availableProcessors() * 2 + 1;
    private final List<DataSourceProperties> dataSourceProperties;

    @Bean
    @ConfigurationProperties(prefix = "data-sources")
    public List<DataSourceProperties> dataSourceProperties() {
        return dataSourceProperties;
    }

    @Bean
    public DataSource dataSource() {
        DataSourceRouting routing = new DataSourceRouting();
        final Map<Object, Object> targetDataSources = this.buildDataSources()
                .entrySet()
                .stream()
                .collect(Collectors.toUnmodifiableMap(Map.Entry::getKey, Map.Entry::getValue));

        routing.setTargetDataSources(targetDataSources);
        routing.setDefaultTargetDataSource(targetDataSources.values().stream().filter(Objects::nonNull).findFirst().get());

        return routing;
    }

    @Bean(name = "datasourceMap")
    public Map<String, DataSource> buildDataSources() {
        List<DataSourceProperties> dataSources = dataSourceProperties();
        return dataSources.stream()
                .collect(Collectors.toMap(DataSourceProperties::getName, this::createDataSource));
    }

    private DataSource createDataSource(DataSourceProperties properties) {
        HikariConfig config = new HikariConfig();
        config.setJdbcUrl(properties.getUrl());
        config.setUsername(properties.getUser());
        config.setPassword(properties.getPassword());
        config.setPoolName(String.format("%s-db-pool", properties.getName()));
        config.setDriverClassName(DataSourceDriverResolver.resolveDriver(properties.getStrategy()));
        config.setMaximumPoolSize(MAXIMUM_POOL_SIZE);
        config.setAutoCommit(true);
        return new HikariDataSource(config);
    }
}
