package com.dhr.controllers.respond;

import com.dhr.model.Respond;
import com.dhr.model.RespondFeedback;
import com.dhr.services.RespondFeedbackService;
import com.dhr.services.RespondService;
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
@RequestMapping("/api/v1/vacancies/{vacancyId}/responds/{respondId}/review")
public class RespondReviewRestController {
    @Autowired
    RespondFeedbackService respondFeedbackService;

    @Autowired
    RespondService respondService;

    @GetMapping
    public List<RespondFeedback> getAllByRespondId(@PathVariable String respondId) {
        return respondFeedbackService.getAllByRespondId(respondId);
    }

    @PostMapping
    public ResponseEntity createRespondFeedback(@RequestBody RespondFeedback respondFeedback,
                                                @PathVariable String respondId) {
        Respond respond = respondService.get(respondId).get();
        respondFeedback.setRespond(respond);
        return new ResponseEntity<>(respondFeedbackService.save(respondFeedback), HttpStatus.CREATED);
    }

}
