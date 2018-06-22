package com.dhr.services;

import com.dhr.model.User;

import java.util.List;
import java.util.Optional;

public interface UserService {
    String save(String companyId, User user);

    void delete(User user);

    User update(User user);

    Optional<User> get(String id);

    Iterable<User> getAll();

    List<User> getByCompanyId(String companyId);

    User getByLogin(String name);
}
