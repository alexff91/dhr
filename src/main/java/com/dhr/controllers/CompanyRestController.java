package com.dhr.controllers;

import com.dhr.model.Company;
import com.dhr.model.User;
import com.dhr.model.Vacancy;
import com.dhr.services.CompanyServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
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

    private final CompanyServiceImpl companyService;

    public CompanyRestController(CompanyServiceImpl companyService) {
        this.companyService = companyService;
    }

    @GetMapping("/{companyId}/vacancies")
    public ResponseEntity<List<Vacancy>> getVacanciesByCompanyId(@PathVariable Long companyId) {
        return companyService.get(companyId)
                .map(company -> ResponseEntity.ok(company.getVacancies()))
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/{companyId}/users")
    public ResponseEntity<List<User>> getUsersByCompanyId(@PathVariable Long companyId) {
        Optional<Company> company = companyService.get(companyId);
        return company.map(c -> new ResponseEntity<>(c.getUsers(), OK))
                .orElseGet(() -> new ResponseEntity<>(NOT_FOUND));
    }

    @GetMapping("/{companyId}")
    public ResponseEntity<Company> getByCompanyId(@PathVariable Long companyId) {
        return companyService.get(companyId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @ResponseStatus(CREATED)
    public ResponseEntity<Company> createCompany(@RequestBody Company company) {
        Company savedCompany = companyService.save(company);
        return new ResponseEntity<>(savedCompany, CREATED);
    }

    @DeleteMapping("/{companyId}")
    public ResponseEntity<Void> deleteCompany(@PathVariable Long companyId) {
        return companyService.get(companyId)
                .map(company -> {
                    companyService.delete(company);
                    return new ResponseEntity<Void>(OK);
                })
                .orElse(new ResponseEntity<>(NOT_FOUND));
    }

    @GetMapping
    public ResponseEntity<List<Company>> getCompanies() {
        return ResponseEntity.ok(companyService.getAll());
    }
}