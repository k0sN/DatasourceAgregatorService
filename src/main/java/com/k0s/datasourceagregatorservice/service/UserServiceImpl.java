package com.k0s.datasourceagregatorservice.service;


import com.k0s.datasourceagregatorservice.entity.User;
import com.k0s.datasourceagregatorservice.repository.UserRepository;
import com.k0s.datasourceagregatorservice.util.UserComparator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    public List<User> findAll(Optional<String> sortBy, Optional<String> sortOrder) {
        List<User> users = userRepository.findAll();
        return sortUsers(users, sortBy, sortOrder);
    }

    @Override
    public List<User> findByRequestParams(Optional<String> usernameFilter,
                                          Optional<String> nameFilter,
                                          Optional<String> surnameFilter,
                                          Optional<String> sortBy,
                                          Optional<String> sortOrder
    ) {
        List<User> users = userRepository.findByRequestParams(usernameFilter, nameFilter, surnameFilter);
        return sortUsers(users, sortBy, sortOrder);
    }

    private List<User> sortUsers(List<User> users, Optional<String> sortBy, Optional<String> sortOrder) {
        return users.stream()
                .sorted(UserComparator.getComparator(sortBy, sortOrder))
                .collect(Collectors.toList());
    }
}
