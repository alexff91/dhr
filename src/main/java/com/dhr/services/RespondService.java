package com.dhr.services;

import com.dhr.model.Respond;

import java.util.List;
import java.util.Optional;

public interface RespondService {
    Long save(Respond interview);

    void delete(Respond interview);

    void update(Respond interview);

    Optional<Respond> get(Long id);

    List<Respond> getAll();

    List<Respond> getAllByInterviewId(Long interviewId);

    List<Respond> getByInterviewIdAndRespondId(Long interviewId, Long respondId);
}
