package com.dhr.services;

import com.dhr.model.Respond;
import com.dhr.model.RespondQuestion;
import com.dhr.repositories.RespondQuestionRepository;
import com.dhr.repositories.RespondRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class RespondQuestionServiceImpl implements RespondQuestionService {
    @Autowired
    private RespondQuestionRepository repository;

    @Autowired
    private RespondRepository respondRepository;

    @Override
    public RespondQuestion save(RespondQuestion question, String respondId) {
        Respond respond = respondRepository.findById(respondId).get();
        question.setRespond(respond);
        return repository.save(question);
    }

    @Override
    public void delete(RespondQuestion question) {
        repository.delete(question);
    }

    @Override
    public RespondQuestion update(RespondQuestion question) {
        RespondQuestion oldQuestion = repository.findById(question.getId()).get();
        oldQuestion.setDurationMax(question.getDurationMax());
        oldQuestion.setDurationToRead(question.getDurationToRead());
        oldQuestion.setIsCompulsory(question.getIsCompulsory());
        oldQuestion.setOrderNumber(question.getOrderNumber());
        oldQuestion.setQuestion(question.getQuestion());
        repository.save(question);
        return question;
    }

    @Override
    public Optional<RespondQuestion> get(Long id) {
        return repository.findById(id);
    }

    @Override
    public Iterable<RespondQuestion> getAll() {
        return repository.findAll();
    }

    @Override
    public List<RespondQuestion> getAllByRespond(String id) {
        return repository.findAllByRespondId(id);
    }
}
