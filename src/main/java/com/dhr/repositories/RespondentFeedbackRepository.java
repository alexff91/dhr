package com.dhr.repositories;

import com.dhr.model.RespondentFeedback;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RespondentFeedbackRepository extends JpaRepository<RespondentFeedback, String> {
    List<RespondentFeedback> findAllByRespondId(String respondId);
}