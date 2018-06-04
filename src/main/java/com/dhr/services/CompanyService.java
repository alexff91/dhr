package com.dhr.services;

import com.dhr.model.Company;

import java.util.Optional;

public interface CompanyService {
    Long save(Company company);

    void delete(Company company);

    Company update(Company company);

    Optional<Company> get(Long id);

    Iterable<Company> getAll();
}
