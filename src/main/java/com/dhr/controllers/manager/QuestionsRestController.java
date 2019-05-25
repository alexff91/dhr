package com.dhr.controllers.manager;

import com.dhr.model.Question;
import com.dhr.services.QuestionServiceImpl;
import com.dhr.services.VacanciesServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/v1/secured/vacancies/{vacancyId}/questions")
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
        questionService.update(question, vacancyId);
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
