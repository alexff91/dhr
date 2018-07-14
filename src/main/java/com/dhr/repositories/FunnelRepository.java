package com.dhr.repositories;

import com.dhr.model.VacancyFunnel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FunnelRepository extends JpaRepository<VacancyFunnel, Long> {
    VacancyFunnel getOneByVacancyId(String vacancyId);
}