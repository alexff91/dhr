package com.dhr.repositories;

import com.dhr.model.Question;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface QuestionRepository extends CrudRepository<Question, Long> {
    public List<Question> findAllByVacancyId(Long vacancyId);
}