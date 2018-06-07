package com.dhr.services;

import com.dhr.model.User;
import com.dhr.repositories.CompanyRepository;
import com.dhr.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository repository;

    @Autowired
    private CompanyRepository companyRepository;

    @Override
    public Long save(Long companyId, User user) {
        user.setCompany(companyRepository.findById(companyId).get());
        repository.save(user);
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
    public Optional<User> get(Long id) {
        return repository.findById(id);
    }

    @Override
    public Iterable<User> getAll() {
        return repository.findAll();
    }

    @Override
    public List<User> getByCompanyId(Long companyId) {
        return repository.findAllByCompanyId(companyId);
    }
}
