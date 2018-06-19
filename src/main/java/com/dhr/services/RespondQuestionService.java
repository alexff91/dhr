package com.dhr.services;

import com.dhr.model.RespondQuestion;

import java.util.List;
import java.util.Optional;

public interface RespondQuestionService {
    RespondQuestion save(RespondQuestion question, String respondId);

    void delete(RespondQuestion question);

    RespondQuestion update(RespondQuestion question);

    Optional<RespondQuestion> get(Long id);

    Iterable<RespondQuestion> getAll();

    List<RespondQuestion> getAllByRespond(String id);
}
