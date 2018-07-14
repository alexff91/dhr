package com.dhr.services;

import com.dhr.model.VacancyFunnel;

import javax.transaction.NotSupportedException;

public interface FunnelService {
    VacancyFunnel save(VacancyFunnel funnel);

    void delete(VacancyFunnel funnel) throws NotSupportedException;

    VacancyFunnel update(VacancyFunnel funnel);

    VacancyFunnel get(Long id);

    Iterable<VacancyFunnel> getAll();

    VacancyFunnel getByVacancy(String vacancyId);
}
