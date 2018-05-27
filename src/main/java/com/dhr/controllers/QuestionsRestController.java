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
import org.springframework.web.bind.annotation.RestController;

import javax.websocket.server.PathParam;
import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/v1/vacancies/{vacancyId}/questions")
public class QuestionsRestController {
    @Autowired
    QuestionServiceImpl questionService;

    @Autowired
    VacanciesServiceImpl vacancyService;

    @GetMapping
    public List<Question> getQuestionsByVacancy(@PathVariable Long vacancyId) {
        return questionService.getAllByVacancy(vacancyId);
    }

    @PostMapping
    public ResponseEntity create(@PathVariable Long vacancyId,@RequestBody Question question) {
        questionService.save(question);
        Vacancy vacancy = vacancyService.get(vacancyId).get();
        vacancy.getQuestions().add(question);
        vacancyService.save(vacancy);
        return new ResponseEntity(HttpStatus.CREATED);
    }

    @DeleteMapping("/{questionId}")
    public ResponseEntity delete(@PathVariable Long questionId) {
        questionService.delete(questionService.get(questionId).get());
        return new ResponseEntity(HttpStatus.OK);
    }
}
