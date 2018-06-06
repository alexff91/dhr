package com.dhr.services;

import com.dhr.model.Question;
import com.dhr.repositories.QuestionRepository;
import com.dhr.repositories.VacancyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Service
public class QuestionServiceImpl implements QuestionService {
    @Autowired
    private QuestionRepository repository;
    @Autowired
    private VacancyRepository vacancyRepository;

    @Override
    public Long save(Question question, String vacancyId) {
        question.setVacancy(vacancyRepository.findById(vacancyId).get());
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
    public Iterable<Question> getAll() {
        return repository.findAll();
    }

    @Override
    public List<Question> getAllByVacancy(String id) {
        return repository.findAllByVacancyId(id);
    }
}
