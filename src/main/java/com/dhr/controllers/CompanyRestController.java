package com.dhr.controllers;

import com.dhr.model.Company;
import com.dhr.model.Vacancy;
import com.dhr.services.CompanyServiceImpl;
import com.dhr.services.VacanciesServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

import static org.springframework.http.HttpStatus.*;

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

    @RequestMapping(value = "/{companyId}/users", method = RequestMethod.GET)
    public ResponseEntity getUsersByCompanyId(@PathVariable Long companyId) {
        Optional<Company> company = companyService.get(companyId);
        return company.map(c -> new ResponseEntity(c.getUsers(), OK))
                .orElseGet(() -> new ResponseEntity(NOT_FOUND));
    }

    @RequestMapping(value = "/{companyId}", method = RequestMethod.GET)
    public Company getByCompanyId(@PathVariable Long companyId) {
        return companyService.get(companyId).get();
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(CREATED)
    public ResponseEntity<Long> createCompany(@RequestBody Company company) {
        return new ResponseEntity<>(companyService.save(company), CREATED);
    }

    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(OK)
    public ResponseEntity updateCompany(@RequestBody Company company) {
        Company oldCompany = companyService.get(company.getId()).get();
        oldCompany.setCompanyDescription(company.getCompanyDescription());
        oldCompany.setCompanyName(company.getCompanyName());
        oldCompany.setCompanyLogoPath(company.getCompanyLogoPath());
        companyService.save(oldCompany);
        return new ResponseEntity(OK);
    }

    @DeleteMapping("{companyId}")
    public ResponseEntity deleteCompany(@PathVariable Long companyId) {
        companyService.delete(companyService.get(companyId).get());
        return new ResponseEntity(OK);
    }

    @GetMapping
    public Iterable<Company> getCompanies() {
        return companyService.getAll();
    }
}