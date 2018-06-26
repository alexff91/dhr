package com.dhr.services;

import com.dhr.model.QuestionAnswerFeedback;
import com.dhr.repositories.QuestionAnswerFeedbackRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
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
        QuestionAnswerFeedback questionAnswerIdAndUserId = repository.findOneByQuestionAnswerIdAndUserId(questionAnswerId, userId);
        if (questionAnswerIdAndUserId == null) {
            feedback.setUser(userService.get(userId).get());
            feedback.setQuestionAnswer(questionAnswerService.get(questionAnswerId).get());
            QuestionAnswerFeedback save = repository.save(feedback);
            return save.getId();
        } else {
            questionAnswerIdAndUserId.setComment(feedback.getComment());
            questionAnswerIdAndUserId.setDate(new Date());
            questionAnswerIdAndUserId.setSkillsFeedback(feedback.getSkillsFeedback());
            QuestionAnswerFeedback save = repository.save(questionAnswerIdAndUserId);
            return save.getId();
        }

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
    public QuestionAnswerFeedback findOneByQuestionAnswerId(Long questionAnswerId, String userId) {
        return repository.findOneByQuestionAnswerIdAndUserId(questionAnswerId, userId);
    }
}
