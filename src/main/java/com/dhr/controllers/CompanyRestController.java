package com.dhr.controllers;

import com.dhr.model.Company;
import com.dhr.model.Vacancy;
import com.dhr.services.CompanyServiceImpl;
import com.dhr.services.VacanciesServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/v1/companies")
public class CompanyRestController {
    @Autowired
    CompanyServiceImpl companyService;
    @Autowired
    VacanciesServiceImpl vacancyService;

    @RequestMapping(value = "/{companyId}/vacancies", method = RequestMethod.GET)
    public List<Vacancy> getVacanciesByCompanyId(@PathVariable Long companyId) {
        return companyService.get(companyId).get().getVacancies();
    }

    @RequestMapping(value = "/{companyId}", method = RequestMethod.GET)
    public Company getByCompanyId(@PathVariable Long companyId) {
        return companyService.get(companyId).get();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity createCompany(@RequestBody Company company) {
        companyService.save(company);
        return new ResponseEntity(HttpStatus.CREATED);
    }

    @DeleteMapping("{id}")
    public ResponseEntity deleteCompany(@PathVariable Long companyId) {
        companyService.delete(companyService.get(companyId).get());
        return new ResponseEntity(HttpStatus.OK);
    }

    @GetMapping
    public List<Company> getCompanies() {
        return companyService.getAll();
    }
}