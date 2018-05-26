package com.dhr.services;

import com.dhr.model.Company;
import com.dhr.repositories.CompanyRepository;
import com.dhr.repositories.VacancyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CompanyServiceImpl implements CompanyService {
    @Autowired
    private CompanyRepository repository;

    @Autowired
    private VacancyRepository vacancyRepository;

    @Override
    public Long save(Company company) {
        repository.save(company);
        company.getVacancies().forEach(i -> vacancyRepository.save(i));
        return company.getCompanyId();
    }

    @Override
    public void delete(Company company) {
        company.getVacancies().forEach(i -> vacancyRepository.delete(i));
        repository.delete(company);
    }

    @Override
    public Company update(Company company) {
        repository.save(company);
        return company;
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
