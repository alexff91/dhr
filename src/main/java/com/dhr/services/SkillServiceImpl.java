package com.dhr.services;

import com.dhr.model.Skill;
import com.dhr.repositories.SkillRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
public class SkillServiceImpl implements SkillService {
    @Autowired
    private SkillRepository repository;

    @Override
    public Skill save(Skill skill) {
        Skill foundSkill = repository.findOneByName(skill.getName());
        if (foundSkill == null) {
            return repository.save(skill);
        } else return foundSkill;
    }

    @Override
    public void delete(Skill skill) {
        repository.delete(skill);
    }

    @Override
    public Skill update(Skill skill) {
        repository.save(skill);
        return skill;
    }

    @Override
    public Optional<Skill> get(Long id) {
        return repository.findById(id);
    }

    @Override
    public Iterable<Skill> getAll() {
        return repository.findAll();
    }
}
