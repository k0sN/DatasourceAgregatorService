package com.k0s.datasourceagregatorservice.repository;



import com.k0s.datasourceagregatorservice.configuration.DataSourceContextHolder;
import com.k0s.datasourceagregatorservice.configuration.properties.DataSourceProperties;
import com.k0s.datasourceagregatorservice.entity.User;
import com.k0s.datasourceagregatorservice.service.UserRowMapper;
import com.k0s.datasourceagregatorservice.util.QueryGenerator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

@Repository
@RequiredArgsConstructor
@Slf4j
public class UserRepository {

    private final JdbcTemplate jdbcTemplate;
    private final List<DataSourceProperties> dataSourceProperties;

    public List<User> findAll() {
        return fetchUsersFromAllDataSources(this::fetchAllUsersFromCurrentDataSource);
    }

    public List<User> findByRequestParams(Optional<String> usernameFilter, Optional<String> nameFilter, Optional<String> surnameFilter) {
        return fetchUsersFromAllDataSources(dataSource ->
                fetchFilterUsersFromCurrentDataSource(usernameFilter, nameFilter, surnameFilter, dataSource));
    }

    private List<User> fetchUsersFromAllDataSources(Function<DataSourceProperties, List<User>> fetchFunction) {
        List<User> allUsers = new ArrayList<>();

        dataSourceProperties.forEach(dataSourceProperty -> {
            try {
                DataSourceContextHolder.setDataSourceKey(dataSourceProperty.getName());
                List<User> usersFromCurrentDataSource = fetchFunction.apply(dataSourceProperty);
                allUsers.addAll(usersFromCurrentDataSource);
            } catch (Exception ex) {
                log.error("Transaction error. Database: {}, table: {}", dataSourceProperty.getName(), dataSourceProperty.getTable(), ex);
            } finally {
                DataSourceContextHolder.clearDataSourceKey();
            }
        });

        return allUsers;
    }

    private List<User> fetchAllUsersFromCurrentDataSource(DataSourceProperties dataSourceProperty) {
        String selectQuery = QueryGenerator.generateFindAllQuery(dataSourceProperty);
        log.info("QUERY Database {} : {}", dataSourceProperty.getName(), selectQuery);
        return jdbcTemplate.query(selectQuery, new UserRowMapper(dataSourceProperty.getMapping()));

    }

    private List<User> fetchFilterUsersFromCurrentDataSource(Optional<String> usernameFilter, Optional<String> nameFilter, Optional<String> surnameFilter, DataSourceProperties dataSourceProperty) {
        String selectQuery = QueryGenerator.generateFilterQuery(dataSourceProperty);
        log.info("QUERY Database {} : {}", dataSourceProperty.getName(), selectQuery);
        return jdbcTemplate.query(selectQuery,
                new UserRowMapper(dataSourceProperty.getMapping()),
                QueryGenerator.normalizeParams(usernameFilter, nameFilter, surnameFilter));

    }

}
