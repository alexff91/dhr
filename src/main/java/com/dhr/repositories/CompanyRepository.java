package com.dhr.repositories;

import com.dhr.model.Company;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface CompanyRepository extends MongoRepository<Company, Long> {
}