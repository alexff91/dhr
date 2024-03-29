package com.dhr.services;

import com.dhr.model.Question;
import com.dhr.repositories.QuestionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Service
public class QuestionServiceImpl implements QuestionService {
    @Autowired
    private QuestionRepository repository;

    @Override
    public Long save(Question question) {
        repository.save(question);
        return question.getId();
    }

    @Override
    public void delete(Question question) {
        repository.delete(question);
    }

    @Override
    public Question update(Question question) {
        repository.save(question);
        return question;
    }

    @Override
    public Optional<Question> get(Long id) {
        return repository.findById(id);
    }

    @Override
    public List<Question> getAll() {
        return repository.findAll();
    }

    @Override
    public List<Question> getAllByVacancy(Long id) {
        return repository.findAllByVacancyId(id);
    }
}
