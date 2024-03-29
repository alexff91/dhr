package com.dhr.controllers;

import com.dhr.model.Question;
import com.dhr.model.Vacancy;
import com.dhr.services.QuestionServiceImpl;
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
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/v1/vacancies")
public class VacancyRestController {
    @Autowired
    VacanciesServiceImpl vacanciesService;

    @Autowired
    QuestionServiceImpl questionService;

    @RequestMapping(value = "{vacancyId}/questions", method = RequestMethod.GET)
    public List<Question> getQuestionsByVacancyId(@PathVariable Long vacancyId) {
        return vacanciesService.get(vacancyId).get().getQuestions();
    }

    @RequestMapping(value = "{vacancyId}", method = RequestMethod.GET)
    public Vacancy getByVacancyId(@PathVariable Long vacancyId) {
        return vacanciesService.get(vacancyId).get();
    }

    @PostMapping
    public ResponseEntity createVacancy(@RequestBody Vacancy vacancy) {
        vacanciesService.save(vacancy);
        return new ResponseEntity(HttpStatus.CREATED);
    }

    @GetMapping
    public List<Vacancy> getVacancies() {
        return vacanciesService.getAll();
    }

    @DeleteMapping("/{vacancyId}")
    public ResponseEntity deleteVacancy(@PathVariable Long vacancyId) {
        vacanciesService.delete(vacanciesService.get(vacancyId).get());
        return new ResponseEntity(HttpStatus.OK);
    }
}
