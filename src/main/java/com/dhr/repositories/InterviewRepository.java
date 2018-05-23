package com.dhr.repositories;

import com.dhr.model.Interview;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface InterviewRepository extends MongoRepository<Interview, Long> {
    Interview findFirstByInterviewId(Long interviewId);
}