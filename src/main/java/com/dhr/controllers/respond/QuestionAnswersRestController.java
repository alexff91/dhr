package com.dhr.controllers.respond;

import com.dhr.model.QuestionAnswer;
import com.dhr.services.QuestionAnswerServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/v1/secured/respond/{respondId}/answers")
public class QuestionAnswersRestController {
    @Autowired
    QuestionAnswerServiceImpl questionService;

    @GetMapping
    public List<QuestionAnswer> getQuestionsByRespondId(@PathVariable String respondId) {
        return questionService.getAllByRespondId(respondId);
    }
}
