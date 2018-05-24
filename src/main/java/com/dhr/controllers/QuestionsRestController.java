package com.dhr.controllers;

import com.dhr.model.Question;
import com.dhr.services.QuestionServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
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

    @GetMapping("/{interviewId}")
    public List<Question> getQuestionsByInterview(@PathParam("interviewId") Long id) {
        return questionService.getAllByInterview(id);
    }
}
