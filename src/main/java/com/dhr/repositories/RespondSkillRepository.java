package com.dhr.repositories;

import com.dhr.model.RespondSkill;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RespondSkillRepository extends JpaRepository<RespondSkill, Long> {
    List<RespondSkill> findAllByCompanyId(Long companyId);
}