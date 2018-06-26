package com.dhr.repositories;

import com.dhr.model.Skill;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SkillRepository extends JpaRepository<Skill, Long> {
    List<Skill> findAllByCompanyId(String companyId);
    Skill findOneByName(String name);
    List<Skill> findAllByDeleted(Boolean deleted);
    Optional<Skill> findOneByIdAndDeleted(Long id, Boolean deleted);
}