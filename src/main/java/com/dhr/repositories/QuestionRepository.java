package com.dhr.repositories;

import com.dhr.model.Question;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface QuestionRepository extends MongoRepository<Question, Long> {
    public Question findFirstById(Long questionId);
}