package com.dhr.repositories;

import com.dhr.model.Vacancy;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VacancyRepository extends CrudRepository<Vacancy, String> {
    List<Vacancy> findAllByCompanyId(Long companyId);
}