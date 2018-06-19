package com.dhr.services;

import com.dhr.model.RespondSkill;

import java.util.Optional;

public interface RespondSkillService {
    Long save(RespondSkill skill);

    void delete(RespondSkill skill);

    RespondSkill update(RespondSkill skill);

    Optional<RespondSkill> get(Long id);

    Iterable<RespondSkill> getAll();
}
