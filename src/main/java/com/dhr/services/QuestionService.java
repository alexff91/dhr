package com.dhr.services;

import com.dhr.model.Question;

import java.util.List;
import java.util.Optional;

public interface QuestionService {
    void save(Question question);

    void delete(Question question);

    void update(Question question);

    Optional<Question> get(Long id);

    List<Question> getAll();
}
