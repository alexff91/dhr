package com.dhr.services;

import com.dhr.model.Vacancy;

import java.util.Optional;

public interface VacancyService {
    String save(Vacancy vacancy);

    void increaseViewCounter(String vacancyId);

    void delete(Vacancy vacancy);

    void update(String vacancyId, Vacancy vacancy);

    Optional<Vacancy> get(String id);

    Iterable<Vacancy> getAll();

    void updateRespondCount(Vacancy respondVacancy);

    void restore(String vacancyId);

    void copy(String vacancyId);
}
