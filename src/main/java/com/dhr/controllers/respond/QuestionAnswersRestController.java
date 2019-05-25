package com.dhr.controllers.respond;

import com.dhr.model.QuestionAnswer;
import com.dhr.services.QuestionAnswerServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
