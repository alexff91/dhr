package com.dhr.services;

import com.dhr.model.RespondFeedback;
import com.dhr.repositories.RespondFeedbackRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class RespondFeedbackServiceImpl implements RespondFeedbackService {
    @Autowired
    private RespondFeedbackRepository repository;

    @Override
    public RespondFeedback save(RespondFeedback respond) {
        if (respond.getDate() == null) {
            respond.setDate(new Date());
        }
        repository.save(respond);
        return repository.findById(respond.getId()).get();
    }

    @Override
    public void delete(RespondFeedback respond) {
        repository.delete(respond);
    }

    @Override
    public void update(RespondFeedback respond) {
        repository.save(respond);
    }

    @Override
    public Optional<RespondFeedback> get(Long id) {
        return repository.findById(id);
    }

    @Override
    public Iterable<RespondFeedback> getAll() {
        return repository.findAll();
    }

    @Override
    public List<RespondFeedback> getAllByRespondId(String respondId) {
        return repository.findAllByRespondId(respondId);
    }
}
