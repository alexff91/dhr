package com.dhr.controllers;

import com.dhr.model.QuestionRespond;
import com.dhr.services.QuestionRespondServiceImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/v1/respond/{respondId}/questionResponds")
public class QuestionRespondRestController {

    private final QuestionRespondServiceImpl questionService;

    public QuestionRespondRestController(QuestionRespondServiceImpl questionService) {
        this.questionService = questionService;
    }

    @GetMapping
    public ResponseEntity<List<QuestionRespond>> getQuestionsByVacancy(@PathVariable String respondId) {
        return ResponseEntity.ok(questionService.getAllByRespondId(respondId));
    }
}
