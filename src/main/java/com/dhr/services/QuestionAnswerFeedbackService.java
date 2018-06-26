package com.dhr.services;

import com.dhr.model.QuestionAnswerFeedback;

import java.util.Map;
import java.util.Optional;

public interface QuestionAnswerFeedbackService {
    Long save(Long questionAnswerId, String userId, QuestionAnswerFeedback feedback);

    void delete(QuestionAnswerFeedback feedback);

    QuestionAnswerFeedback update(QuestionAnswerFeedback feedback);

    Optional<QuestionAnswerFeedback> get(Long id);

    Iterable<QuestionAnswerFeedback> getAll();

    Iterable<QuestionAnswerFeedback> getAllByQuestionAnswerId(Long id);

    QuestionAnswerFeedback findOneByQuestionAnswerId(Long questionAnswerId, String userId);

    Map<String,Double> getSkillResponds(String respondId);
}
