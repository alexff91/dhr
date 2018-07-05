package com.dhr.controllers.statistic;

import com.dhr.model.RespondentFeedback;
import com.dhr.services.RespondentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/v1/respondentFeedback")
public class RespondentRestController {
    @Autowired
    RespondentService respondentService;

    @PostMapping("/{respondId}")
    public ResponseEntity<RespondentFeedback> saveResponentFeedback(@RequestBody RespondentFeedback respondentFeedback, @PathVariable String respondId) {
        return new ResponseEntity<>(respondentService.save(respondentFeedback, respondId), HttpStatus.CREATED);
    }
}