package com.dhr.services;

import com.dhr.model.Vacancy;

import java.util.Optional;

public interface VacancyService {
    String save(Vacancy vacancy);

    void delete(Vacancy vacancy);

    void update(Vacancy vacancy);

    Optional<Vacancy> get(String id);

    Iterable<Vacancy> getAll();
}
