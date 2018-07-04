package com.dhr.services;

import com.dhr.model.RespondentFeedback;
import com.dhr.repositories.RespondRepository;
import com.dhr.repositories.RespondentFeedbackRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
public class RespondentServiceImpl implements RespondentService {
    @Autowired
    RespondentFeedbackRepository respondentRepository;
    @Autowired
    RespondRepository respondRepository;

    @Override
    public RespondentFeedback save(RespondentFeedback respondentFeedback, String respondId) {
        respondentFeedback.setRespond(respondRepository.getOne(respondId));
        return respondentRepository.save(respondentFeedback);
    }

    @Override
    public void delete(RespondentFeedback respondentFeedback) {

    }

    @Override
    public void update(RespondentFeedback respondentFeedback) {

    }

    @Override
    public Optional<RespondentFeedback> get(String respondId) {
        return respondentRepository.findById(respondId);
    }

    @Override
    public Iterable<RespondentFeedback> getAll() {
        return respondentRepository.findAll();
    }
}
