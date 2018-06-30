package com.dhr.services;

import com.dhr.model.QuestionSkill;
import com.dhr.repositories.QuestionSkillRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
public class QuestionSkillServiceImpl implements QuestionSkillService {
    @Autowired
    private QuestionSkillRepository repository;

    @Override
    public QuestionSkill save(QuestionSkill skill) {
        return repository.save(skill);
    }

    @Override
    public void delete(QuestionSkill skill) {
        skill.setDeleted(true);
        repository.save(skill);
    }

    @Override
    public QuestionSkill update(QuestionSkill skill) {
        repository.save(skill);
        return skill;
    }

    @Override
    public Optional<QuestionSkill> get(Long id) {
        return repository.findById(id);
    }

    @Override
    public Iterable<QuestionSkill> getAll() {
        return repository.findAllByDeleted(false);
    }
}
