package com.dhr.repositories;

import com.dhr.model.QuestionAnswer;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuestionRespondRepository extends CrudRepository<QuestionAnswer, Long> {
    List<QuestionAnswer> findAllByRespondId(String respondId);
}