package com.dhr.services;

import com.dhr.model.QuestionAnswerFeedback;
import com.dhr.repositories.QuestionAnswerFeedbackRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
public class QuestionAnswerFeedbackServiceIml implements QuestionAnswerFeedbackService {
    @Autowired
    private QuestionAnswerFeedbackRepository repository;

    @Override
    public Long save(QuestionAnswerFeedback feedback) {
        repository.save(feedback);
        return feedback.getId();
    }

    @Override
    public void delete(QuestionAnswerFeedback feedback) {
        repository.delete(feedback);
    }

    @Override
    public QuestionAnswerFeedback update(QuestionAnswerFeedback feedback) {
        repository.save(feedback);
        return feedback;
    }

    @Override
    public Optional<QuestionAnswerFeedback> get(Long id) {
        return repository.findById(id);
    }

    @Override
    public Iterable<QuestionAnswerFeedback> getAll() {
        return repository.findAll();
    }
}
