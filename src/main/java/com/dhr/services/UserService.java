package com.dhr.services;

import com.dhr.model.User;

import java.util.Optional;

public interface UserService {
    Long save(User company);

    void delete(User company);

    User update(User company);

    Optional<User> get(Long id);

    Iterable<User> getAll();
}
