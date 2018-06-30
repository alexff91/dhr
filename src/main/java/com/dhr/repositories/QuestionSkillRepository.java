package com.dhr.repositories;

import com.dhr.model.QuestionSkill;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface QuestionSkillRepository extends JpaRepository<QuestionSkill, Long> {
    QuestionSkill findOneByName(String name);

    List<QuestionSkill> findAllByDeleted(Boolean deleted);

    Optional<QuestionSkill> findOneByIdAndDeleted(Long id, Boolean deleted);
}