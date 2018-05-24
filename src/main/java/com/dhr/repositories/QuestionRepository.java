package com.dhr.repositories;

import com.dhr.model.Question;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface QuestionRepository extends MongoRepository<Question, Long> {
    public List<Question> findAllByInterviewId(Long interviewId);
}