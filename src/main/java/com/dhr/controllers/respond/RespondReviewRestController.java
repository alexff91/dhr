package com.dhr.controllers.respond;

import com.dhr.model.RespondFeedback;
import com.dhr.services.RespondFeedbackService;
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

import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/v1/secured/responds/{respondId}/")
public class RespondReviewRestController {
    @Autowired
    RespondFeedbackService respondFeedbackService;

    @Autowired
    RespondService respondService;

    @Autowired
    UserService userService;

    @GetMapping("/review")
    public List<RespondFeedback> getAllByRespondId(@PathVariable String respondId) {
        return respondFeedbackService.getAllByRespondId(respondId);
    }

    @PostMapping("/users/{userId}/review")
    public ResponseEntity createRespondFeedback(@PathVariable String respondId,
                                                @PathVariable Long userId,
                                                @RequestBody RespondFeedback respondFeedback) {
        return new ResponseEntity<>(respondFeedbackService.save(respondId, userId, respondFeedback), HttpStatus.CREATED);
    }
}
