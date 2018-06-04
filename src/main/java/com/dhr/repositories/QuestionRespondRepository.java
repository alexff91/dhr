package com.dhr.repositories;

import com.dhr.model.QuestionRespond;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface QuestionRespondRepository extends CrudRepository<QuestionRespond, Long> {
    public List<QuestionRespond> findAllByRespondId(String respondId);
}