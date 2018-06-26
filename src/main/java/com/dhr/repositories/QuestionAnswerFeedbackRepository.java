package com.dhr.repositories;

import com.dhr.model.QuestionAnswerFeedback;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuestionAnswerFeedbackRepository extends JpaRepository<QuestionAnswerFeedback, Long> {
    List<QuestionAnswerFeedback> findAllByQuestionAnswerId(Long questionAnswerId);

    Iterable<QuestionAnswerFeedback> findAllByQuestionAnswerIdAndUserId(Long questionAnswerId, String userId);
}