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
@RequestMapping("/api/v1/secured/answers/{questionAnswerId}/")
public class AnswerReviewRestController {
    @Autowired
    QuestionAnswerFeedbackService feedbackService;

    @Autowired
    RespondService respondService;

    @Autowired
    UserService userService;

    @GetMapping("/review")
    public Iterable<QuestionAnswerFeedback> getAllByAnswerId(@PathVariable Long questionAnswerId) {
        return feedbackService.getAllByQuestionAnswerId(questionAnswerId);
    }

    @GetMapping("/review/user/{userId}")
    public Iterable<QuestionAnswerFeedback> getAllReviewByUserId(@PathVariable Long questionAnswerId,
                                                                 @PathVariable String userId) {
        return feedbackService.findAllByQuestionAnswerIdAndUserId(userId, questionAnswerId);
    }

    @PostMapping("/user/{userId}/review")
    public ResponseEntity createAnswerFeedback(@PathVariable Long questionAnswerId,
                                               @PathVariable String userId,
                                               @RequestBody QuestionAnswerFeedback answerFeedback
    ) {
        return new ResponseEntity<>(feedbackService.save(questionAnswerId, userId, answerFeedback), HttpStatus.CREATED);
    }

}
