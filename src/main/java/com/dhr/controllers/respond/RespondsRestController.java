package com.dhr.controllers.respond;

import com.dhr.model.Respond;
import com.dhr.model.User;
import com.dhr.model.dto.RespondCommentDto;
import com.dhr.model.enums.ReviewStatus;
import com.dhr.services.CompanyService;
import com.dhr.services.QuestionAnswerFeedbackService;
import com.dhr.services.RespondServiceImpl;
import com.dhr.services.UserService;
import com.dhr.services.VacancyService;
import com.dhr.view.View;
import com.fasterxml.jackson.annotation.JsonView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;
import java.util.Objects;

import static org.springframework.http.HttpStatus.OK;

@CrossOrigin(origins = "*")
@RestController
public class RespondsRestController {
    @Autowired
    RespondServiceImpl respondService;

    @Autowired
    VacancyService vacancyService;

    @Autowired
    CompanyService companyService;

    @Autowired
    QuestionAnswerFeedbackService feedbackService;

    @Autowired
    private UserService userService;

    @RequestMapping(value = "/api/v1/secured/myResponds", method = RequestMethod.GET)
    public ResponseEntity<String> getMyResponds() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentPrincipalName = authentication.getName();
        User user = userService.getByLogin(currentPrincipalName);
        StringBuilder response = new StringBuilder();
        final int[] counter = {0};
        companyService.getVacanciesByCompanyId(user.getCompany().getId()).forEach(vacancy ->
        {

            respondService.getAllByVacancyId(vacancy.getId()).forEach(respond -> {
                if (respond.getReviewResponds().size() == 0 || respond.getReviewResponds().stream().filter(respondFeedback ->
                        Objects.equals(respondFeedback.getUser().getLogin(), user.getLogin())).count() == 0) {
                    if (counter[0] <= 5) {
                        counter[0]++;
                        response.append(vacancy.getPosition()).append(" / ")
                                .append(respond.getName())
                                .append(" ")
                                .append(respond.getLastName().length() > 0 ? respond.getLastName().toCharArray()[0] + ".: " : " : ")
                                .append("https://dashboard.vi-hr.com/vacancies/")
                                .append(vacancy.getId())
                                .append("/responses/")
                                .append(respond.getId())
                                .append("/review  ;\r\n");
                    }
                }
            });

        });
        return new ResponseEntity<>(response.toString(), OK);
    }


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
    public ResponseEntity<Respond> createRespond(@RequestBody Respond respond,
                                                 @PathVariable String vacancyId) {
        return new ResponseEntity<>(respondService.save(respond, vacancyId), HttpStatus.OK);
    }

    @PostMapping("/api/v1/secured/vacancies/{vacancyId}/responds/{respondId}/decline")
    public ResponseEntity declineRespond(@PathVariable String respondId,
                                         @PathVariable String vacancyId,
                                         @RequestBody RespondCommentDto comment) {
        Respond respond = respondService.get(respondId).get();
        respond.setReviewStatus(ReviewStatus.DECLINED);
        respond.setComment(comment.getComment());
        respondService.update(respond);
        return new ResponseEntity(HttpStatus.OK);
    }

    @PostMapping("/api/v1/secured/vacancies/{vacancyId}/responds/{respondId}/accept")
    public ResponseEntity acceptRespond(@PathVariable String respondId,
                                        @PathVariable String vacancyId,
                                        @RequestBody RespondCommentDto comment) {
        Respond respond = respondService.get(respondId).get();
        respond.setReviewStatus(ReviewStatus.ACCEPTED);
        respond.setComment(comment.getComment());
        respondService.update(respond);
        return new ResponseEntity(HttpStatus.OK);
    }

    @GetMapping("/api/v1/secured/responds/{respondId}/skillsSummary")
    public ResponseEntity<Map<String, Double>> skillsSummary(@PathVariable String respondId) {
        Map<String, Double> skillsSummary = feedbackService.getSkillResponds(respondId);
        return new ResponseEntity<>(skillsSummary, HttpStatus.OK);
    }

    @PostMapping("/api/v1/secured/vacancies/{vacancyId}/responds/{respondId}/review")
    public ResponseEntity onReviewRespond(@PathVariable String respondId,
                                          @PathVariable String vacancyId) {
        Respond respond = respondService.get(respondId).get();
        respond.setReviewStatus(ReviewStatus.ON_REVIEW);
        respondService.update(respond);
        return new ResponseEntity(HttpStatus.OK);
    }

    @PostMapping("/api/v1/secured/vacancies/{vacancyId}/responds/{respondId}/block")
    public ResponseEntity blockRespond(@PathVariable String respondId,
                                       @PathVariable String vacancyId,
                                       @RequestBody RespondCommentDto comment) {
        Respond respond = respondService.get(respondId).get();
        respond.setReviewStatus(ReviewStatus.BLOCKED);
        respond.setComment(comment.getComment());
        respondService.update(respond);
        return new ResponseEntity(HttpStatus.OK);
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
