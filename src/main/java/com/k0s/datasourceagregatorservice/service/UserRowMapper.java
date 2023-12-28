package com.k0s.datasourceagregatorservice.service;

import com.testtask.simplerest.entity.User;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;


public class UserRowMapper implements RowMapper<User> {
    private final Map<String, String> mapping;

    public UserRowMapper(Map<String, String> mapping) {
        this.mapping = mapping;
    }

    @Override
    public User mapRow(ResultSet resultSet, int rowNum) throws SQLException {
        User user = new User();
        user.setId(resultSet.getString(mapping.get("id")));
        user.setUsername(resultSet.getString(mapping.get("username")));
        user.setName(resultSet.getString(mapping.get("name")));
        user.setSurname(resultSet.getString(mapping.get("surname")));
        return user;
    }
}
