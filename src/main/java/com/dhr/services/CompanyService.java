package com.dhr.services;

import com.dhr.model.Company;
import com.dhr.model.Skill;
import com.dhr.model.Vacancy;

import java.util.List;
import java.util.Optional;

public interface CompanyService {
    String save(Company company);

    void delete(Company company);

    Company update(Company company);

    Optional<Company> get(String id);

    List<Vacancy> getVacanciesByCompanyId(String companyId);

    List<Skill> getSkillsByCompanyId(String companyId);

    Iterable<Company> getAll();
}
