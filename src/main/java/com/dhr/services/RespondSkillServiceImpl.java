package com.dhr.services;

import com.dhr.model.RespondSkill;
import com.dhr.model.Skill;
import com.dhr.repositories.RespondSkillRepository;
import com.dhr.repositories.SkillRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
public class RespondSkillServiceImpl implements RespondSkillService {
    @Autowired
    private RespondSkillRepository repository;

    @Override
    public Long save(RespondSkill skill) {
        repository.save(skill);
        return skill.getId();
    }

    @Override
    public void delete(RespondSkill skill) {
        repository.delete(skill);
    }

    @Override
    public RespondSkill update(RespondSkill skill) {
        repository.save(skill);
        return skill;
    }

    @Override
    public Optional<RespondSkill> get(Long id) {
        return repository.findById(id);
    }

    @Override
    public Iterable<RespondSkill> getAll() {
        return repository.findAll();
    }
}