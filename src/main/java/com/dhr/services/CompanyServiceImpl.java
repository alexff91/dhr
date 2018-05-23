package com.dhr.services;

import com.dhr.model.Company;
import com.dhr.repositories.CompanyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CompanyServiceImpl implements CompanyService {
    @Autowired
    private CompanyRepository repository;

    @Override
    public void save(Company company) {
        repository.save(company);
    }

    @Override
    public void delete(Company company) {
        repository.delete(company);
    }

    @Override
    public void update(Company company) {
        repository.save(company);
    }

    @Override
    public Optional<Company> get(Long id) {
        return repository.findById(id);
    }

    @Override
    public List<Company> getAll() {
        return repository.findAll();
    }
}
