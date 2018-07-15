package com.dhr.controllers.manager;

import com.dhr.model.Question;
import com.dhr.model.Vacancy;
import com.dhr.model.VacancyFunnel;
import com.dhr.services.FunnelService;
import com.dhr.services.QuestionService;
import com.dhr.services.VacancyService;
import com.dhr.view.View;
import com.fasterxml.jackson.annotation.JsonView;
import com.google.common.collect.Sets;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;

@RestController
@CrossOrigin(origins = "*")
public class VacancyRestController {
    @Autowired
    VacancyService vacanciesService;

    @Autowired
    QuestionService questionService;

    @Autowired
    FunnelService funnelService;

    @RequestMapping(value = "/api/v1/vacancies/{vacancyId}/questions", method = RequestMethod.GET)
    public Set<Question> getQuestionsByVacancyId(@PathVariable String vacancyId) {
        return Sets.newHashSet(questionService.getAllByVacancy(vacancyId));
    }

    @PutMapping("/api/v1/secured/vacancies/{vacancyId}")
    public ResponseEntity<Object> updateVacancy(@PathVariable String vacancyId, @RequestBody Vacancy vacancy) {
        vacanciesService.update(vacancyId, vacancy);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping("/api/v1/secured/vacancies/{vacancyId}/restore")
    public ResponseEntity restoreVacancy(@PathVariable String vacancyId) {
        vacanciesService.restore(vacancyId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping("/api/v1/secured/vacancies/{vacancyId}/copy")
    public ResponseEntity copyVacancy(@PathVariable String vacancyId) {
        vacanciesService.copy(vacancyId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/api/v1/vacancies/{vacancyId}/stat")
    public ResponseEntity increaseViewCounter(@PathVariable String vacancyId) {
        vacanciesService.increaseViewCounter(vacancyId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @RequestMapping(value = "/api/v1/vacancies/{vacancyId}", method = RequestMethod.GET)
    public Vacancy getByVacancyId(@PathVariable String vacancyId) {
        return vacanciesService.get(vacancyId).get();
    }

    @JsonView(View.Detail.class)
    @RequestMapping(value = "/api/v1/secured/vacancies/{vacancyId}/detailed", method = RequestMethod.GET)
    public Vacancy getByVacancyIdDetailed(@PathVariable String vacancyId) {
        return vacanciesService.get(vacancyId).get();
    }

    @GetMapping("/api/v1/vacancies/")
    public Iterable<Vacancy> getVacancies() {
        return vacanciesService.getAll();
    }

    @GetMapping("/api/v1/vacancies/{vacancyId}/statistics")
    public ResponseEntity<VacancyFunnel> getStatistic(@PathVariable String vacancyId) {
        return new ResponseEntity<>(funnelService.getByVacancy(vacancyId), HttpStatus.OK);
    }

    @JsonView(View.Detail.class)
    @GetMapping("/api/v1/secured/vacancies/detailed")
    public Iterable<Vacancy> getVacanciesDetailed() {
        return vacanciesService.getAll();
    }

    @DeleteMapping("/api/v1/secured/vacancies/{vacancyId}")
    public ResponseEntity deleteVacancy(@PathVariable String vacancyId) {
        vacanciesService.delete(vacanciesService.get(vacancyId).get());
        return new ResponseEntity(HttpStatus.OK);
    }
}
