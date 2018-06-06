package com.dhr.services;

import com.dhr.model.User;

import java.util.List;
import java.util.Optional;

public interface UserService {
    Long save(User user);

    void delete(User user);

    User update(User user);

    Optional<User> get(Long id);

    Iterable<User> getAll();

    List<User> getByCompanyId(Long companyId);
}
