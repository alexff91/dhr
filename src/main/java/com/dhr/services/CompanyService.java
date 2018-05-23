package com.dhr.services;

import com.dhr.model.Company;

import java.util.List;
import java.util.Optional;

public interface CompanyService {
    void save(Company company);

    void delete(Company company);

    void update(Company company);

    Optional<Company> get(Long id);

    List<Company> getAll();
}
