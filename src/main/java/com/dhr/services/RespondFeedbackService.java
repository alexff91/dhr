package com.dhr.services;

import com.dhr.model.RespondFeedback;

import java.util.List;
import java.util.Optional;

public interface RespondFeedbackService {
    RespondFeedback save(RespondFeedback vacancy);

    void delete(RespondFeedback vacancy);

    void update(RespondFeedback vacancy);

    Optional<RespondFeedback> get(Long id);

    Iterable<RespondFeedback> getAll();

    List<RespondFeedback> getAllByRespondId(String respondId);

}
