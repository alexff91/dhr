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

    @Autowired
    private UserService userService;

    @Autowired
    private QuestionAnswerService questionAnswerService;

    @Override
    public Long save(Long questionAnswerId, String userId, QuestionAnswerFeedback feedback) {
        feedback.setUser(userService.get(userId).get());
        feedback.setQuestionAnswer(questionAnswerService.get(questionAnswerId).get());
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

    @Override
    public Iterable<QuestionAnswerFeedback> getAllByQuestionAnswerId(Long id) {
        return repository.findAllByQuestionAnswerId(id);
    }

    @Override
    public Iterable<QuestionAnswerFeedback> findAllByQuestionAnswerIdAndUserId(String userId, Long questionAnswerId) {
        return repository.findAllByQuestionAnswerIdAndUserId(userId, questionAnswerId);
    }
}
