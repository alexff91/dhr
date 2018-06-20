package com.dhr.services;

import com.dhr.model.QuestionAnswerFeedback;

import java.util.Optional;

public interface QuestionAnswerFeedbackService {
    Long save(Long questionAnswerId, Long userId, QuestionAnswerFeedback feedback);

    void delete(QuestionAnswerFeedback feedback);

    QuestionAnswerFeedback update(QuestionAnswerFeedback feedback);

    Optional<QuestionAnswerFeedback> get(Long id);

    Iterable<QuestionAnswerFeedback> getAll();

    Iterable<QuestionAnswerFeedback> getAllByQuestionAnswerId(Long id);

    Iterable<QuestionAnswerFeedback> findAllByQuestionAnswerIdAndUserId(Long userId, Long questionAnswerId);
}
