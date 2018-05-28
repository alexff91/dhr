package com.dhr.services;

import com.dhr.model.Respond;

import java.util.List;
import java.util.Optional;

public interface RespondService {
    Respond save(Respond vacancy);

    void delete(Respond vacancy);

    void update(Respond vacancy);

    Optional<Respond> get(String id);

    List<Respond> getAll();

    List<Respond> getAllByVacancyId(Long vacancyId);

    List<Respond> getByVacancyIdAndRespondId(Long vacancyId, String respondId);
}
