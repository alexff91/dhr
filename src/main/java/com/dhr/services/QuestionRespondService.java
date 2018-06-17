package com.dhr.services;

import com.dhr.model.QuestionAnswer;

import java.util.List;
import java.util.Optional;

public interface QuestionRespondService {
    Long save(QuestionAnswer question);

    void delete(QuestionAnswer question);

    QuestionAnswer update(QuestionAnswer question);

    Optional<QuestionAnswer> get(Long id);

    Iterable<QuestionAnswer> getAll();

    List<QuestionAnswer> getAllByRespondId(String id);
}
