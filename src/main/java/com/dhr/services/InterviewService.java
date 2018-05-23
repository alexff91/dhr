package com.dhr.services;

import com.dhr.model.Interview;

import java.util.List;
import java.util.Optional;

public interface InterviewService {
    void save(Interview interview);

    void delete(Interview interview);

    void update(Interview interview);

    Optional<Interview> get(Long id);

    List<Interview> getAll();
}
