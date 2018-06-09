package com.dhr.services;

import com.dhr.model.QuestionRespond;
import com.dhr.repositories.QuestionRespondRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class QuestionRespondServiceImpl implements QuestionRespondService {
    @Autowired
    private QuestionRespondRepository repository;

    @Override
    public Long save(QuestionRespond question) {
        repository.save(question);
        return question.getId();
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
    public Iterable<QuestionRespond> getAll() {
        return repository.findAll();
    }

    @Override
    public List<QuestionRespond> getAllByRespondId(String id) {
        return repository.findAllByRespondId(id);
    }
}
