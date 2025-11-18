package com.dhr.controllers;

import com.dhr.model.Question;
import com.dhr.model.Vacancy;
import com.dhr.services.VacanciesServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/v1/vacancies")
public class VacancyRestController {

    private final VacanciesServiceImpl vacanciesService;

    public VacancyRestController(VacanciesServiceImpl vacanciesService) {
        this.vacanciesService = vacanciesService;
    }

    @GetMapping("/{vacancyId}/questions")
    public ResponseEntity<List<Question>> getQuestionsByVacancyId(@PathVariable Long vacancyId) {
        return vacanciesService.get(vacancyId)
                .map(vacancy -> ResponseEntity.ok(vacancy.getQuestions()))
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/{vacancyId}")
    public ResponseEntity<Vacancy> getByVacancyId(@PathVariable Long vacancyId) {
        return vacanciesService.get(vacancyId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Vacancy> createVacancy(@RequestBody Vacancy vacancy) {
        Vacancy savedVacancy = vacanciesService.save(vacancy);
        return new ResponseEntity<>(savedVacancy, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<Vacancy>> getVacancies() {
        return ResponseEntity.ok(vacanciesService.getAll());
    }

    @DeleteMapping("/{vacancyId}")
    public ResponseEntity<Void> deleteVacancy(@PathVariable Long vacancyId) {
        return vacanciesService.get(vacancyId)
                .map(vacancy -> {
                    vacanciesService.delete(vacancy);
                    return new ResponseEntity<Void>(HttpStatus.OK);
                })
                .orElse(ResponseEntity.notFound().build());
    }
}
