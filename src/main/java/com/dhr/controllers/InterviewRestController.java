package com.dhr.controllers;

import com.dhr.model.Interview;
import com.dhr.model.Question;
import com.dhr.services.InterviewServiceImpl;
import com.dhr.services.QuestionServiceImpl;
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

import java.util.List;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/v1/interviews")
public class InterviewRestController {
    @Autowired
    InterviewServiceImpl interviewService;

    @Autowired
    QuestionServiceImpl questionService;

    @RequestMapping(value = "{interviewId}/questions", method = RequestMethod.GET)
    public List<Question> getQuestionsByInterviewId(@PathVariable Long interviewId) {
        return interviewService.get(interviewId).get().getQuestions();
    }

    @PostMapping
    public ResponseEntity createInterview(@RequestBody Interview interview){
        interviewService.save(interview);
        return new ResponseEntity(HttpStatus.OK);
    }

    @GetMapping
    public List<Interview> getInterviews() {
        return interviewService.getAll();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteInterview(@PathVariable Long id) {
        interviewService.delete(interviewService.get(id).get());
        return new ResponseEntity(HttpStatus.OK);
    }
}
