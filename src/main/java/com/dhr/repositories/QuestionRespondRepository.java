package com.dhr.repositories;

import com.dhr.model.QuestionRespond;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface QuestionRespondRepository extends MongoRepository<QuestionRespond, Long> {
    public List<QuestionRespond> findAllByRespondId(Long respondId);
}