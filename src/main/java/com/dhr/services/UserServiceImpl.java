package com.dhr.services;

import com.dhr.model.Role;
import com.dhr.model.User;
import com.dhr.repositories.CompanyRepository;
import com.dhr.repositories.RoleRepository;
import com.dhr.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository repository;

    @Autowired
    private CompanyRepository companyRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Override
    public String save(String companyId, User user) {
        user.setCompany(companyRepository.findById(companyId).get());
        user.setId(Integer.toHexString(user.hashCode()));
        User savedUser = repository.save(user);
        user.getRoles().forEach(role -> {
            roleRepository.save(Role.builder().name(role.getName()).user(savedUser).build());
        });
        return user.getId();
    }

    @Override
    public void delete(User user) {
        repository.delete(user);
    }

    @Override
    public User update(User user) {
        repository.save(user);
        return user;
    }

    @Override
    public Optional<User> get(String id) {
        return repository.findById(id);
    }

    @Override
    public Iterable<User> getAll() {
        return repository.findAll();
    }

    @Override
    public List<User> getByCompanyId(String companyId) {
        return repository.findAllByCompanyId(companyId);
    }

    @Override
    public User getByLogin(String name) {
        return repository.findByLogin(name);
    }
}
