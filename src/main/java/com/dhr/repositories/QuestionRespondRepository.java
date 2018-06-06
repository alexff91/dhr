package com.dhr.repositories;

import com.dhr.model.QuestionRespond;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuestionRespondRepository extends CrudRepository<QuestionRespond, Long> {
    List<QuestionRespond> findAllByRespondId(String respondId);
}