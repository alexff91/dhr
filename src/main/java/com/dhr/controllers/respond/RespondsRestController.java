package com.dhr.controllers.respond;

import com.dhr.model.Respond;
import com.dhr.model.enums.ReviewStatus;
import com.dhr.services.QuestionAnswerFeedbackService;
import com.dhr.services.RespondServiceImpl;
import com.dhr.services.VacancyService;
import com.dhr.view.View;
import com.fasterxml.jackson.annotation.JsonView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@CrossOrigin(origins = "*")
@RestController
public class RespondsRestController {
    @Autowired
    RespondServiceImpl respondService;

    @Autowired
    VacancyService vacancyService;

    @Autowired
    QuestionAnswerFeedbackService feedbackService;

    @GetMapping("/api/v1/secured/vacancies/{vacancyId}/responds")
    public List<Respond> getRespondsByVacancy(@PathVariable String vacancyId) {
        return respondService.getAllByVacancyId(vacancyId);
    }

    @JsonView(View.Detail.class)
    @GetMapping("/api/v1/secured/vacancies/{vacancyId}/responds/detailed")
    public List<Respond> getRespondsByVacancyDetailed(@PathVariable String vacancyId) {
        return respondService.getAllByVacancyId(vacancyId);
    }

    @PostMapping("/api/v1/vacancies/{vacancyId}/responds")
    public ResponseEntity createRespond(@RequestBody Respond respond,
                                        @PathVariable String vacancyId) {
        respondService.update(respond);
        return new ResponseEntity( HttpStatus.OK);
    }

    @PostMapping("/api/v1/secured/vacancies/{vacancyId}/responds/{respondId}/decline")
    public ResponseEntity declineRespond(@PathVariable String respondId,
                                         @PathVariable String vacancyId,
                                         @RequestBody String comment) {
        Respond respond = respondService.get(respondId).get();
        respond.setReviewStatus(ReviewStatus.DECLINED);
        respond.setComment(comment);
        respondService.update(respond);
        return new ResponseEntity( HttpStatus.OK);
    }

    @PostMapping("/api/v1/secured/vacancies/{vacancyId}/responds/{respondId}/accept")
    public ResponseEntity acceptRespond(@PathVariable String respondId,
                                        @PathVariable String vacancyId,
                                        @RequestBody String comment) {
        Respond respond = respondService.get(respondId).get();
        respond.setReviewStatus(ReviewStatus.ACCEPTED);
        respond.setComment(comment);
        respondService.update(respond);
        return new ResponseEntity( HttpStatus.OK);
    }

    @GetMapping("/api/v1/secured/responds/{respondId}/skillsSummary")
    public ResponseEntity<Map<String, Double>> skillsSummary(@PathVariable String respondId) {
        Map<String, Double> skillsSummary = feedbackService.getSkillResponds(respondId);
        return new ResponseEntity(skillsSummary, HttpStatus.OK);
    }

    @PostMapping("/api/v1/secured/vacancies/{vacancyId}/responds/{respondId}/review")
    public ResponseEntity onReviewRespond(@PathVariable String respondId,
                                          @PathVariable String vacancyId) {
        Respond respond = respondService.get(respondId).get();
        respond.setReviewStatus(ReviewStatus.ON_REVIEW);
        respondService.update(respond);
        return new ResponseEntity( HttpStatus.OK);
    }

    @PostMapping("/api/v1/secured/vacancies/{vacancyId}/responds/{respondId}/block")
    public ResponseEntity blockRespond(@PathVariable String respondId,
                                       @PathVariable String vacancyId,
                                       @RequestBody String comment) {
        Respond respond = respondService.get(respondId).get();
        respond.setReviewStatus(ReviewStatus.BLOCKED);
        respond.setComment(comment);
        respondService.update(respond);
        return new ResponseEntity( HttpStatus.OK);
    }

    @GetMapping("/api/v1/secured/vacancies/{vacancyId}/responds/{respondId}")
    public Respond getRespondById(@PathVariable String vacancyId,
                                  @PathVariable String respondId) {
        return respondService.getByVacancyIdAndRespondId(vacancyId, respondId);
    }

    @JsonView(View.Detail.class)
    @GetMapping("/api/v1/secured/vacancies/{vacancyId}/responds/{respondId}/detailed")
    public Respond getRespondByIdDetailed(@PathVariable String vacancyId,
                                          @PathVariable String respondId) {
        return respondService.getByVacancyIdAndRespondId(vacancyId, respondId);
    }
}
