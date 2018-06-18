package com.dhr.services;

import com.dhr.model.QuestionAnswerFeedback;

import java.util.Optional;

public interface QuestionAnswerFeedbackService {
    Long save(QuestionAnswerFeedback feedback);

    void delete(QuestionAnswerFeedback feedback);

    QuestionAnswerFeedback update(QuestionAnswerFeedback feedback);

    Optional<QuestionAnswerFeedback> get(Long id);

    Iterable<QuestionAnswerFeedback> getAll();
}
