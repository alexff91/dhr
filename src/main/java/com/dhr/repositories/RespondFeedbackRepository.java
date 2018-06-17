package com.dhr.repositories;

import com.dhr.model.RespondFeedback;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RespondFeedbackRepository extends JpaRepository<RespondFeedback, Long> {
    List<RespondFeedback> findAllByRespondId(String respondId);
}