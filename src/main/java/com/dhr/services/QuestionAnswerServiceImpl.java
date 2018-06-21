package com.dhr.services;

import com.dhr.model.QuestionAnswer;
import com.dhr.repositories.QuestionRespondRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class QuestionAnswerServiceImpl implements QuestionAnswerService {
    @Autowired
    private QuestionRespondRepository repository;

    @Override
    public QuestionAnswer save(QuestionAnswer question) {
        return repository.save(question);
    }

    @Override
    public void delete(QuestionAnswer question) {
        repository.delete(question);
    }

    @Override
    public QuestionAnswer update(QuestionAnswer question) {
        repository.save(question);
        return question;
    }

    @Override
    public Optional<QuestionAnswer> get(Long id) {
        return repository.findById(id);
    }

    @Override
    public Iterable<QuestionAnswer> getAll() {
        return repository.findAll();
    }

    @Override
    public List<QuestionAnswer> getAllByRespondId(String id) {
        return repository.findAllByRespondId(id);
    }
}
