package com.dhr.services;

import com.dhr.model.Question;

import java.util.List;
import java.util.Optional;

public interface QuestionService {
    Long save(Question question);

    void delete(Question question);

    Question update(Question question);

    Optional<Question> get(Long id);

    List<Question> getAll();

    List<Question> getAllByVacancy(Long id);
}
