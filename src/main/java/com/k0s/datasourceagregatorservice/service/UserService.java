package com.k0s.datasourceagregatorservice.service;


import com.k0s.datasourceagregatorservice.entity.User;

import java.util.List;
import java.util.Optional;

public interface UserService {
    List<User> findAll(Optional<String> sortBy, Optional<String> sortOrder);

    List<User> findByRequestParams(
            Optional<String> username,
            Optional<String> name,
            Optional<String> surname,
            Optional<String> sortBy,
            Optional<String> sortOrder
    );
}
