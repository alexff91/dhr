package com.dhr.services;

import com.dhr.model.Skill;
import com.dhr.model.Vacancy;
import com.dhr.repositories.SkillRepository;
import com.dhr.repositories.VacancyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.Optional;

@Service
@Transactional
public class VacanciesServiceImpl implements VacancyService {
    @Autowired
    private VacancyRepository repository;

    @Autowired
    private SkillRepository skillRepository;

    @Override
    public String save(Vacancy vacancy) {
        vacancy.setId(Integer.toHexString(vacancy.hashCode()) + Long.toHexString(new Date().getTime()));
        vacancy.getQuestions().forEach(question -> {
            question.getSkills().forEach(skill -> {
                Skill existSkill = skillRepository.findOneByName(skill.getName());
                if(existSkill != null){
                    question.getSkills().remove(skill);
                    question.getSkills().add(existSkill);
                }
                else {
                    Skill savedSkill = skillRepository.save(skill);
                    question.getSkills().remove(skill);
                    question.getSkills().add(savedSkill);
                }
            });
            question.setVacancy(vacancy);
        });
        repository.save(vacancy);
        return vacancy.getId();
    }

    @Override
    public void increaseViewCounter(String vacancyId) {
        Vacancy vacancy = repository.findById(vacancyId).get();
        vacancy.setViewsCount(vacancy.getViewsCount() + 1);
        repository.save(vacancy);
    }

    @Override
    public void delete(Vacancy vacancy) {
        repository.delete(vacancy);
    }

    @Override
    public void update(String vacancyId, Vacancy vacancy) {
        Vacancy oldVacancy = repository.findById(vacancyId).get();
        oldVacancy.setDescription(vacancy.getDescription());
        oldVacancy.setPosition(vacancy.getPosition());
        vacancy.getQuestions().forEach(question -> question.setVacancy(oldVacancy));
        oldVacancy.getQuestions().clear();
        oldVacancy.getQuestions().addAll(vacancy.getQuestions());
        oldVacancy.setUpdateDate(new Date());
        repository.save(oldVacancy);
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
