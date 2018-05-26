package com.dhr.services;

import com.dhr.model.QuestionRespond;
import com.dhr.repositories.QuestionRespondRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class QuestionRespondServiceImpl implements QuestionRespondService {
    @Autowired
    private QuestionRespondRepository repository;

    @Override
    public Long save(QuestionRespond question) {
        repository.save(question);
        return question.getQuestionRespondId();
    }

    @Override
    public void delete(QuestionRespond question) {
        repository.delete(question);
    }

    @Override
    public QuestionRespond update(QuestionRespond question) {
        repository.save(question);
        return question;
    }

    @Override
    public Optional<QuestionRespond> get(Long id) {
        return repository.findById(id);
    }

    @Override
    public List<QuestionRespond> getAll() {
        return repository.findAll();
    }

    @Override
    public List<QuestionRespond> getAllByRespondId(Long id) {
        return repository.findAllByRespondId(id);
    }
}
