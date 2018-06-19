package com.dhr.repositories;

import com.dhr.model.RespondQuestion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RespondQuestionRepository extends JpaRepository<RespondQuestion, Long> {
    List<RespondQuestion> findAllByRespondId(String respondId);
}