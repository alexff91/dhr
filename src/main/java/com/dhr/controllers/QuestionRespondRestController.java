package com.dhr.controllers;

import com.dhr.model.QuestionRespond;
import com.dhr.services.QuestionRespondServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
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
    @Autowired
    QuestionRespondServiceImpl questionService;

    @GetMapping
    public List<QuestionRespond> getQuestionsByVacancy(@PathVariable String respondId) {
        return questionService.getAllByRespondId(respondId);
    }
}
