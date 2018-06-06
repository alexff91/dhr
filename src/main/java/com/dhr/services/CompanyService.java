package com.dhr.services;

import com.dhr.model.Company;
import com.dhr.model.Vacancy;

import java.util.List;
import java.util.Optional;

public interface CompanyService {
    Long save(Company company);

    void delete(Company company);

    Company update(Company company);

    Optional<Company> get(Long id);

    List<Vacancy> getVacanciesByCompanyId(Long companyId);

    Iterable<Company> getAll();
}
