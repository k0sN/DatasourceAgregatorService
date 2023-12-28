package com.k0s.datasourceagregatorservice.util;


import com.k0s.datasourceagregatorservice.entity.User;

import java.util.Comparator;
import java.util.Optional;

public class UserComparator {

    public static Comparator<User> getComparator(Optional<String> sortBy, Optional<String> sortOrder) {

        Comparator<User> comparator = Comparator.comparing(User::getId);
        if (sortBy.isPresent()) {
            switch (sortBy.get()) {
                case "id" -> comparator = Comparator.comparing(User::getId);
                case "username" -> comparator = Comparator.comparing(User::getUsername);
                case "name" -> comparator = Comparator.comparing(User::getName);
                case "surname" -> comparator = Comparator.comparing(User::getSurname);
            }
        }
        if (sortOrder.map(order -> order.equalsIgnoreCase("desc")).orElse(false)) {
            return comparator.reversed();
        }
        return comparator;
    }
}

