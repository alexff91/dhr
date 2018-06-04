package com.dhr.services;

import com.dhr.model.Company;
import com.dhr.repositories.CompanyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;

@Service
public class CompanyServiceImpl implements CompanyService {
    @Autowired
    private CompanyRepository companyRepository;

    @Override
    public Long save(Company company) {
        company.setCreationDate(new Date());
        companyRepository.save(company);
        return company.getId();
    }

    @Override
    public void delete(Company company) {
        companyRepository.delete(company);
    }

    @Override
    public Company update(Company company) {
        companyRepository.save(company);
        return company;
    }

    @Override
    public Optional<Company> get(Long id) {
        return companyRepository.findById(id);
    }

    @Override
    public Iterable<Company> getAll() {
        return companyRepository.findAll();
    }
}
