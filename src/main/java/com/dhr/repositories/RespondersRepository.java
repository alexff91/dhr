package com.dhr.repositories;

import com.dhr.model.Responder;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface RespondersRepository extends MongoRepository<Responder, Long> {
}