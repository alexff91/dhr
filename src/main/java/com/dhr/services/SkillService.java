package com.dhr.services;

import com.dhr.model.Skill;

import java.util.Optional;

public interface SkillService {
    Skill save(Skill skill);

    void delete(Skill skill);

    Skill update(Skill skill);

    Optional<Skill> get(Long id);

    Iterable<Skill> getAll();
}
