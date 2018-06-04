package com.dhr.repositories;

import com.dhr.model.Respond;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface RespondRepository extends CrudRepository<Respond, String> {
    List<Respond> findAllByVacancyId(Long vacancyId);

    List<Respond> findFirstByVacancyIdAndId(Long vacancyId, String id);
}