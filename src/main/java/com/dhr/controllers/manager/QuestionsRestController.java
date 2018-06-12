package com.dhr.controllers.manager;

import com.dhr.model.Question;
import com.dhr.services.QuestionServiceImpl;
import com.dhr.services.VacanciesServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/v1/vacancies/{vacancyId}/questions")
public class QuestionsRestController {
    @Autowired
    QuestionServiceImpl questionService;

    @Autowired
    VacanciesServiceImpl vacancyService;

    @PostMapping
    public ResponseEntity create(@PathVariable String vacancyId, @RequestBody Question question) {
        questionService.save(question, vacancyId);
        return new ResponseEntity(HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity update(@PathVariable String vacancyId, @RequestBody Question question) {
        Question oldQuestion = questionService.get(question.getId()).get();
        oldQuestion.setDurationMax(question.getDurationMax());
        oldQuestion.setDurationToRead(question.getDurationToRead());
        oldQuestion.setIsCompulsory(question.getIsCompulsory());
        oldQuestion.setOrderNumber(question.getOrderNumber());
        oldQuestion.setQuestion(question.getQuestion());
        questionService.save(oldQuestion, vacancyId);
        return new ResponseEntity(HttpStatus.OK);
    }

    @PostMapping("/batch")
    public ResponseEntity createBatch(@PathVariable String vacancyId, @RequestBody List<Question> questions) {
        questions.forEach(question -> questionService.save(question, vacancyId));
        return new ResponseEntity(HttpStatus.CREATED);
    }

    @DeleteMapping("/{questionId}")
    public ResponseEntity delete(@PathVariable Long questionId) {
        questionService.delete(questionService.get(questionId).get());
        return new ResponseEntity(HttpStatus.OK);
    }
}
