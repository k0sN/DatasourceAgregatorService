package com.k0s.datasourceagregatorservice.util;


import com.k0s.datasourceagregatorservice.configuration.properties.DataSourceProperties;

import java.util.Arrays;
import java.util.Map;
import java.util.Optional;
import java.util.StringJoiner;
import java.util.stream.Stream;

public class QueryGenerator {

    private static final String GET_ALL_USERS_QUERY = "SELECT %s, %s, %s, %s FROM %s";
    private static final String GET_ALL_USERS_FILTER_QUERY = "SELECT %s, %s, %s, %s FROM %s WHERE %s";
    private static final String FILTER_PARAMETER = "LOWER(%s) LIKE LOWER(?)";
    public static final String WILDCARD = "%";
    public static final String PLACEHOLDER = "%s%s%s";

    public static String generateFindAllQuery(DataSourceProperties dataSourceProperty) {
        String table = dataSourceProperty.getTable();
        Map<String, String> mapping = dataSourceProperty.getMapping();
        String id = mapping.get("id");
        String username = mapping.get("username");
        String name = mapping.get("name");
        String surname = mapping.get("surname");

        return String.format(GET_ALL_USERS_QUERY, id, username, name, surname, table);
    }

    public static String generateFilterQuery(DataSourceProperties dataSourceProperty) {
        String table = dataSourceProperty.getTable();
        Map<String, String> mapping = dataSourceProperty.getMapping();
        String id = mapping.get("id");
        String username = mapping.get("username");
        String name = mapping.get("name");
        String surname = mapping.get("surname");

        StringJoiner joiner = new StringJoiner(" OR ");
        Arrays.asList(username, name, surname)
                .forEach(field -> joiner.add(FILTER_PARAMETER.formatted(field)));

        return String.format(GET_ALL_USERS_FILTER_QUERY, id, username, name, surname, table, joiner);
    }

    public static Object[] normalizeParams(Optional<String> usernameFilter, Optional<String> nameFilter, Optional<String> surnameFilter) {
        return Stream.of(usernameFilter, nameFilter, surnameFilter)
                .map(e -> e.map(s -> PLACEHOLDER.formatted(WILDCARD, s, WILDCARD)).orElse(null))
                .toArray(String[]::new);
    }
}
