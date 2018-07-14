package com.dhr.services;

import com.dhr.model.VacancyFunnel;
import com.dhr.repositories.FunnelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.transaction.NotSupportedException;

@Service
@Transactional
public class FunnelServiceImpl implements FunnelService {
    @Autowired
    FunnelRepository funnelRepository;

    @Override
    public VacancyFunnel save(VacancyFunnel funnel) {
        return funnelRepository.save(funnel);
    }

    @Override
    public void delete(VacancyFunnel funnel) throws NotSupportedException {
        throw new NotSupportedException();
    }

    @Override
    public VacancyFunnel update(VacancyFunnel funnel) {
        return funnelRepository.save(funnel);
    }

    @Override
    public VacancyFunnel get(Long id) {
        return funnelRepository.getOne(id);
    }

    @Override
    public Iterable<VacancyFunnel> getAll() {
        return funnelRepository.findAll();
    }

    @Override
    public VacancyFunnel getByVacancy(String vacancyId) {
        return funnelRepository.getOneByVacancyId(vacancyId);
    }
}
