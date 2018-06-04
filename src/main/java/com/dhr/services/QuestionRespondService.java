package com.dhr.services;

import com.dhr.model.QuestionRespond;

import java.util.List;
import java.util.Optional;

public interface QuestionRespondService {
    Long save(QuestionRespond question);

    void delete(QuestionRespond question);

    QuestionRespond update(QuestionRespond question);

    Optional<QuestionRespond> get(Long id);

    Iterable<QuestionRespond> getAll();

    List<QuestionRespond> getAllByRespondId(String id);
}
