package com.dhr.repositories;

import com.dhr.model.QuestionAnswerFeedback;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface QuestionAnswerFeedbackRepository extends JpaRepository<QuestionAnswerFeedback, Long> {
    List<QuestionAnswerFeedback> findAllByQuestionAnswerId(Long questionAnswerId);

    QuestionAnswerFeedback findFirstByQuestionAnswerIdAndUserId(Long questionAnswerId, String userId);

    @Query("select p FROM QuestionAnswerFeedback c LEFT JOIN c.skillsFeedback p  where c.id = :id")
    List<Object> findFirstLastNamesAndGrade(@Param("id") Long id);
}