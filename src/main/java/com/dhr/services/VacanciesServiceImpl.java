package com.dhr.services;

import com.dhr.model.Vacancy;
import com.dhr.repositories.VacancyRepository;
import com.dhr.repositories.QuestionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
public class VacanciesServiceImpl implements VacancyService {
    @Autowired
    private VacancyRepository repository;

    @Override
    public String save(Vacancy vacancy) {
        vacancy.setId(Integer.toHexString(vacancy.hashCode()));
        repository.save(vacancy);
        return vacancy.getId();
    }

    @Override
    public void delete(Vacancy vacancy) {
        repository.delete(vacancy);
    }

    @Override
    public void update(String vacancyId, Vacancy vacancy) {
        Vacancy oldVacancy = repository.findById(vacancy.getId()).get();
        oldVacancy.setDescription(vacancy.getDescription());
        oldVacancy.setPosition(vacancy.getPosition());
        oldVacancy.setQuestions(vacancy.getQuestions());
        repository.save(vacancy);
    }

    @Override
    public Optional<Vacancy> get(String id) {
        return repository.findById(id);
    }

    @Override
    public Iterable<Vacancy> getAll() {
        return repository.findAll();
    }
}
