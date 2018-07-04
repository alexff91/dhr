package com.dhr.services;

import com.dhr.model.Respond;

import java.util.List;
import java.util.Optional;

public interface RespondService {
    Respond save(Respond respond, String vacancyId) throws RespondAnsweredException;

    void delete(Respond vacancy);

    void update(Respond vacancy);

    Optional<Respond> get(String id);

    Iterable<Respond> getAll();

    List<Respond> getAllByVacancyId(String vacancyId);

    Respond getByVacancyIdAndRespondId(String vacancyId, String respondId);
}
