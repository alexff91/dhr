package com.dhr.controllers;

import com.dhr.model.Question;
import com.dhr.model.Vacancy;
import com.dhr.services.QuestionServiceImpl;
import com.dhr.services.VacanciesServiceImpl;
import com.google.common.collect.Sets;
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

import java.util.HashSet;
import java.util.Set;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/v1/vacancies")
public class VacancyRestController {
    @Autowired
    VacanciesServiceImpl vacanciesService;

    @Autowired
    QuestionServiceImpl questionService;

    @RequestMapping(value = "{vacancyId}/questions", method = RequestMethod.GET)
    public Set<Question> getQuestionsByVacancyId(@PathVariable String vacancyId) {
        return Sets.newHashSet(questionService.getAllByVacancy(vacancyId));
    }

    @RequestMapping(value = "{vacancyId}", method = RequestMethod.GET)
    public Vacancy getByVacancyId(@PathVariable String vacancyId) {
        return vacanciesService.get(vacancyId).get();
    }

    @GetMapping
    public Iterable<Vacancy> getVacancies() {
        return vacanciesService.getAll();
    }

    @DeleteMapping("/{vacancyId}")
    public ResponseEntity deleteVacancy(@PathVariable String vacancyId) {
        vacanciesService.delete(vacanciesService.get(vacancyId).get());
        return new ResponseEntity(HttpStatus.OK);
    }
}
