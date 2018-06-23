package com.dhr.services;

import com.dhr.model.Skill;
import com.dhr.model.Vacancy;
import com.dhr.repositories.SkillRepository;
import com.dhr.repositories.VacancyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

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
                skill.setCompany(vacancy.getCompany());
                skillRepository.save(skill);
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
        vacancy.getQuestions().forEach(question -> {
            question.setVacancy(oldVacancy);
            Set<Skill> skills = new HashSet<>();
            question.getSkills().forEach(skill -> {
                        skill.setCompany(oldVacancy.getCompany());
                        Skill savedSkill = skillRepository.save(skill);
                        skills.add(savedSkill);
                    }
            );
            question.getSkills().clear();
            question.getSkills().addAll(skills);
        });
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

    @Override
    public void updateRespondCount(Vacancy respondVacancy) {
        respondVacancy.setUnansweredRespondsCount(respondVacancy.getUnansweredRespondsCount() - 1);
        repository.save(respondVacancy);
    }
}
