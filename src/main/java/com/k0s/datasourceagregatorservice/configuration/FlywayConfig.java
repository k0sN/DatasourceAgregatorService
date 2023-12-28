package com.k0s.datasourceagregatorservice.configuration;

import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.flywaydb.core.Flyway;
import org.flywaydb.core.api.Location;
import org.flywaydb.core.api.configuration.FluentConfiguration;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;
import java.io.File;
import java.util.Map;

@Configuration
@Slf4j
public class FlywayConfig {
    public static final String FLYWAY_MIGRATIONS_LOCATION = "db/%s";
    private final Map<String, DataSource> buildDataSources;

    public FlywayConfig(@Qualifier("datasourceMap") Map<String, DataSource> buildDataSources) {
        this.buildDataSources = buildDataSources;
    }

    @PostConstruct
    public void migrateFlyway() {
        buildDataSources.forEach((key, value) -> {
            log.info("FLYWAY FOR DB {}", key);
            FluentConfiguration config = Flyway.configure()
                    .dataSource(value)
                    .locations(FLYWAY_MIGRATIONS_LOCATION.formatted(key));

            Flyway flyway = new Flyway(config);
            for (Location location : flyway.getConfiguration().getLocations()) {
                log.warn("Flyway location:{}", new File(location.getPath()).getAbsolutePath());
            }
            flyway.migrate();
        });
    }
}
