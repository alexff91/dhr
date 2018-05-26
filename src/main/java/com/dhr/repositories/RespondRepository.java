package com.dhr.repositories;

import com.dhr.model.Respond;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface RespondRepository extends MongoRepository<Respond, Long> {
    List<Respond> findAllByVacancyId(Long vacancyId);

    List<Respond> findFirstByVacancyIdAndRespondId(Long vacancyId, Long respondId);
}