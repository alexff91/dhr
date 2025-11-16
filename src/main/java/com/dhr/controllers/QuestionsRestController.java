package com.dhr.controllers;

import com.dhr.model.Question;
import com.dhr.model.Vacancy;
import com.dhr.services.QuestionServiceImpl;
import com.dhr.services.VacanciesServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/v1/vacancies/{vacancyId}/questions")
public class QuestionsRestController {

    private final QuestionServiceImpl questionService;
    private final VacanciesServiceImpl vacancyService;

    public QuestionsRestController(QuestionServiceImpl questionService, VacanciesServiceImpl vacancyService) {
        this.questionService = questionService;
        this.vacancyService = vacancyService;
    }

    @PostMapping
    public ResponseEntity<Question> create(@PathVariable Long vacancyId, @RequestBody Question question) {
        Question savedQuestion = questionService.save(question);

        return vacancyService.get(vacancyId)
                .map(vacancy -> {
                    vacancy.getQuestions().add(savedQuestion);
                    vacancyService.save(vacancy);
                    return new ResponseEntity<>(savedQuestion, HttpStatus.CREATED);
                })
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @DeleteMapping("/{questionId}")
    public ResponseEntity<Void> delete(@PathVariable Long questionId) {
        return questionService.get(questionId)
                .map(question -> {
                    questionService.delete(question);
                    return new ResponseEntity<Void>(HttpStatus.OK);
                })
                .orElse(ResponseEntity.notFound().build());
    }
}
