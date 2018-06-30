package com.dhr.services;

import com.dhr.model.QuestionSkill;

import java.util.Optional;

public interface QuestionSkillService {
    QuestionSkill save(QuestionSkill skill);

    void delete(QuestionSkill skill);

    QuestionSkill update(QuestionSkill skill);

    Optional<QuestionSkill> get(Long id);

    Iterable<QuestionSkill> getAll();
}
