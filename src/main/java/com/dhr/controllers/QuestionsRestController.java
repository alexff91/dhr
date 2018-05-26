package com.dhr.controllers;

import com.dhr.model.Question;
import com.dhr.services.QuestionServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.websocket.server.PathParam;
import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/v1/questions")
public class QuestionsRestController {
    @Autowired
    QuestionServiceImpl questionService;

    @GetMapping
    public List<Question> getQuestions() {
        return questionService.getAll();
    }

    @GetMapping("/{vacancyId}")
    public List<Question> getQuestionsByVacancy(@PathParam("vacancyId") Long id) {
        return questionService.getAllByVacancy(id);
    }

    @PostMapping
    public ResponseEntity create(@RequestBody Question question) {
        questionService.save(question);
        return new ResponseEntity(HttpStatus.CREATED);
    }

    @DeleteMapping("/{questionId}")
    public ResponseEntity delete(@PathParam("questionId") Long questionId) {
        questionService.delete(questionService.get(questionId).get());
        return new ResponseEntity(HttpStatus.OK);
    }
}
