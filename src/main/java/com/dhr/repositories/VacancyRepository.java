package com.dhr.repositories;

import com.dhr.model.Vacancy;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface VacancyRepository extends MongoRepository<Vacancy, Long> {
}