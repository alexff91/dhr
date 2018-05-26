package com.dhr.services;

import com.dhr.model.Vacancy;
import com.dhr.repositories.VacancyRepository;
import com.dhr.repositories.QuestionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class VacanciesServiceImpl implements VacancyService {
    @Autowired
    private VacancyRepository repository;

    @Autowired
    private QuestionRepository questionRepository;

    @Override
    public Long save(Vacancy vacancy) {
        repository.save(vacancy);
        vacancy.getQuestions().forEach(question -> {
            question.setVacancyId(vacancy.getVacancyId());
            questionRepository.save(question);
        });
        return vacancy.getVacancyId();
    }

    @Override
    public void delete(Vacancy vacancy) {
        vacancy.getQuestions().forEach(q -> questionRepository.delete(q));
        repository.delete(vacancy);
    }

    @Override
    public void update(Vacancy vacancy) {
        repository.save(vacancy);
    }

    @Override
    public Optional<Vacancy> get(Long id) {
        return repository.findById(id);
    }

    @Override
    public List<Vacancy> getAll() {
        return repository.findAll();
    }
}
