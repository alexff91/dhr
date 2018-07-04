package com.dhr.services;

import com.dhr.model.RespondentFeedback;

import java.util.Optional;

public interface RespondentService {
    RespondentFeedback save(RespondentFeedback respondentFeedback, String respondId);

    void delete(RespondentFeedback respondentFeedback);

    void update(RespondentFeedback respondentFeedback);

    Optional<RespondentFeedback> get(String respondId);

    Iterable<RespondentFeedback> getAll();
}
