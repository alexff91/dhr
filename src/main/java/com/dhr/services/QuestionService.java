package com.dhr.services;

import com.dhr.model.Question;

import java.util.List;
import java.util.Optional;

public interface QuestionService {
    Long save(Question question, String vacancyId);

    void delete(Question question);

    Question update(Question question, String vacancyId);

    Optional<Question> get(Long id);

    Iterable<Question> getAll();

    List<Question> getAllByVacancy(String id);
}
