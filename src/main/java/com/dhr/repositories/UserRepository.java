package com.dhr.repositories;

import com.dhr.model.Company;
import com.dhr.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepository extends MongoRepository<User, Long> {
}