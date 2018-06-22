package com.dhr.services;

import com.dhr.model.Role;
import com.dhr.repositories.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class RoleServiceImpl implements RoleService {
    @Autowired
    private RoleRepository repository;

    @Override
    public Long save(Role role) {
        repository.save(role);
        return role.getId();
    }

    @Override
    public void delete(Role Role) {
        repository.delete(Role);
    }

    @Override
    public Role update(Role role) {
        repository.save(role);
        return role;
    }

    @Override
    public Optional<Role> get(Long id) {
        return repository.findById(id);
    }

    @Override
    public List<Role> getByUserId(String userId) {
        return repository.findAllByUserId(userId);
    }

    @Override
    public Iterable<Role> getAll() {
        return repository.findAll();
    }
}
