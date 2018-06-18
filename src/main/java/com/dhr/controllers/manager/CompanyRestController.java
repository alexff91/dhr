package com.dhr.controllers.manager;

import com.dhr.model.Company;
import com.dhr.model.Skill;
import com.dhr.model.User;
import com.dhr.model.Vacancy;
import com.dhr.services.CompanyService;
import com.dhr.services.SkillService;
import com.dhr.services.UserService;
import com.dhr.services.VacancyService;
import com.dhr.view.View;
import com.fasterxml.jackson.annotation.JsonView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.NOT_FOUND;
import static org.springframework.http.HttpStatus.OK;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/v1/companies")
public class CompanyRestController {
    @Autowired
    CompanyService companyService;

    @Autowired
    VacancyService vacancyService;

    @Autowired
    UserService userService;

    @Autowired
    SkillService skillService;

    @RequestMapping(value = "/{companyId}/vacancies", method = RequestMethod.GET)
    public List<Vacancy> getVacanciesByCompanyId(@PathVariable Long companyId) {
        return companyService.getVacanciesByCompanyId(companyId);
    }

    @RequestMapping(value = "/{companyId}/skills", method = RequestMethod.GET)
    public List<Skill> getSkillByCompanyId(@PathVariable Long companyId) {
        return companyService.getSkillsByCompanyId(companyId);
    }

    @RequestMapping(value = "/{companyId}/users", method = RequestMethod.GET)
    public ResponseEntity getUsersByCompanyId(@PathVariable Long companyId) {
        Optional<Company> company = companyService.get(companyId);
        return company.map(c -> new ResponseEntity(userService.getByCompanyId(companyId), OK))
                .orElseGet(() -> new ResponseEntity(NOT_FOUND));
    }

    @JsonView(View.CompanyFull.class)
    @RequestMapping(value = "/{companyId}", method = RequestMethod.GET)
    public Company getByCompanyId(@PathVariable Long companyId) {
        return companyService.get(companyId).get();
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(CREATED)
    public ResponseEntity<Long> createCompany(@RequestBody Company company) {
        return new ResponseEntity<>(companyService.save(company), CREATED);
    }

    @PostMapping("/{companyId}/vacancies")
    public ResponseEntity<String> createVacancy(@PathVariable Long companyId, @RequestBody Vacancy vacancy) {
        vacancy.setCompany(companyService.get(companyId).get());
        String vacancyId = vacancyService.save(vacancy);
        return new ResponseEntity<>(vacancyId, HttpStatus.CREATED);
    }

    @PostMapping("/{companyId}/skills")
    public ResponseEntity<Long> createSkill(@PathVariable Long companyId, @RequestBody Skill skill) {
        skill.setCompany(companyService.get(companyId).get());
        Long skillId = skillService.save(skill);
        return new ResponseEntity<>(skillId, HttpStatus.CREATED);
    }

    @DeleteMapping("/{companyId}/skills/{skillId}")
    public ResponseEntity deleteSkill(@PathVariable Long companyId, @PathVariable Long skillId) {
        Skill skill = skillService.get(skillId).get();
        skillService.delete(skill);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/{companyId}/users")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity createUser(@PathVariable Long companyId, @RequestBody User user) {
        userService.save(companyId, user);
        return new ResponseEntity(HttpStatus.CREATED);
    }


    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(OK)
    public ResponseEntity updateCompany(@RequestBody Company company) {
        companyService.update(company);
        return new ResponseEntity(OK);
    }

    @DeleteMapping("{companyId}")
    public ResponseEntity deleteCompany(@PathVariable Long companyId) {
        companyService.delete(companyService.get(companyId).get());
        return new ResponseEntity(OK);
    }

    @GetMapping
    @JsonView(View.CompanyFull.class)
    public Iterable<Company> getCompanies() {
        return companyService.getAll();
    }
}