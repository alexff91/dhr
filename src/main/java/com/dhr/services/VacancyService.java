package com.dhr.services;

import com.dhr.model.Vacancy;

import java.util.Optional;

public interface VacancyService {
    Long save(Vacancy vacancy);

    void delete(Vacancy vacancy);

    void update(Vacancy vacancy);

    Optional<Vacancy> get(Long id);

    Iterable<Vacancy> getAll();
}
