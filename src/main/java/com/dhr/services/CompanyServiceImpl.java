package com.dhr.services;

import com.dhr.model.Company;
import com.dhr.model.Skill;
import com.dhr.model.Vacancy;
import com.dhr.repositories.CompanyRepository;
import com.dhr.repositories.SkillRepository;
import com.dhr.repositories.VacancyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class CompanyServiceImpl implements CompanyService {
    @Autowired
    private CompanyRepository companyRepository;

    @Autowired
    private VacancyRepository vacancyRepository;

    @Autowired
    private SkillRepository skillRepository;

    @Override
    public Long save(Company company) {
        company.setCreationDate(new Date());
        companyRepository.save(company);
        return company.getId();
    }

    @Override
    public void delete(Company company) {
        companyRepository.delete(company);
    }

    @Override
    public Company update(Company company) {
        Company oldCompany = companyRepository.findById(company.getId()).get();
        oldCompany.setDescription(company.getDescription());
        oldCompany.setName(company.getName());
        oldCompany.setLogo(company.getLogo());
        companyRepository.save(company);
        return company;
    }

    @Override
    public Optional<Company> get(Long id) {
        return companyRepository.findById(id);
    }

    @Override
    public List<Vacancy> getVacanciesByCompanyId(Long companyId) {
        return vacancyRepository.findAllByCompanyId(companyId);
    }

    @Override
    public List<Skill> getSkillsByCompanyId(Long companyId) {
        return skillRepository.findAllByCompanyId(companyId);
    }

    @Override
    public Iterable<Company> getAll() {
        return companyRepository.findAll();
    }
}
