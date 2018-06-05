package com.dhr.services;

import com.dhr.model.Role;

import java.util.Optional;

public interface RoleService {
    Long save(Role role);

    void delete(Role role);

    Role update(Role role);

    Optional<Role> get(Long id);

    Iterable<Role> getAll();
}
