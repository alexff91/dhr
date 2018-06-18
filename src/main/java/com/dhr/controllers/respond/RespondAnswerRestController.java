package com.dhr.controllers.respond;

import com.dhr.model.QuestionAnswerFeedback;
import com.dhr.services.QuestionAnswerFeedbackService;
import com.dhr.services.RespondService;
import com.dhr.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/v1/answers/{questionAnswerId}/review")
public class RespondAnswerRestController {
    @Autowired
    QuestionAnswerFeedbackService feedbackService;

    @Autowired
    RespondService respondService;

    @Autowired
    UserService userService;

    @GetMapping
    public Iterable<QuestionAnswerFeedback> getAllByAnswerId(@PathVariable Long questionAnswerId) {
        return feedbackService.getAllByQuestionAnswerId(questionAnswerId);
    }

    @PostMapping("/user/{userId}")
    public ResponseEntity createAnswerFeedback(@PathVariable Long questionAnswerId,
                                               @PathVariable Long userId,
                                               @RequestBody QuestionAnswerFeedback answerFeedback
    ) {
        return new ResponseEntity<>(feedbackService.save(questionAnswerId, userId, answerFeedback), HttpStatus.CREATED);
    }

}
