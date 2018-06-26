package com.dhr.controllers.respond;

import com.dhr.model.QuestionAnswerFeedback;
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
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.LinkedList;
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
    public ResponseEntity<Respond> createRespond(@RequestBody Respond respond,
                                                 @PathVariable String vacancyId) {
        return new ResponseEntity<>(respondService.save(respond, vacancyId), HttpStatus.CREATED);
    }

    @PostMapping("/api/v1/secured/vacancies/{vacancyId}/responds/{respondId}/decline")
    public ResponseEntity<Respond> declineRespond(@PathVariable String respondId,
                                                  @PathVariable String vacancyId) {
        Respond respond = respondService.get(respondId).get();
        respond.setReviewStatus(ReviewStatus.DECLINED);
        return new ResponseEntity<>(respondService.save(respond, vacancyId), HttpStatus.OK);
    }

    @PostMapping("/api/v1/secured/vacancies/{vacancyId}/responds/{respondId}/accept")
    public ResponseEntity<Respond> acceptRespond(@PathVariable String respondId,
                                                 @PathVariable String vacancyId) {
        Respond respond = respondService.get(respondId).get();
        respond.setReviewStatus(ReviewStatus.ACCEPTED);
        return new ResponseEntity<>(respondService.save(respond, vacancyId), HttpStatus.OK);
    }

    @GetMapping("/api/v1/secured/responds/{respondId}/skillsSummary")
    @Transactional
    public ResponseEntity<Map<String, Long>> skillsSummary(@PathVariable String respondId) {
        Respond respond = respondService.get(respondId).get();
        Map<String, Double> skillsSummary = new HashMap<>();
        respond.getRespondQuestions().forEach(respondQuestion -> respondQuestion.getQuestionAnswers().forEach(questionAnswer -> {
            Iterable<QuestionAnswerFeedback> answerFeedbacksIterable = feedbackService.getAllByQuestionAnswerId(questionAnswer.getId());
            List<QuestionAnswerFeedback> answerFeedbacks = new LinkedList<>();
            answerFeedbacksIterable.forEach(answerFeedbacks::add);
            answerFeedbacks.forEach(feedback -> feedback.getSkillsFeedback().forEach((skillName, skillLevel) ->
            {
                if (skillsSummary.containsKey(skillName)) {
                    Double count = skillsSummary.get(skillName);
                    skillsSummary.put(skillName, count + skillLevel);

                } else {
                    skillsSummary.put(skillName, skillLevel + 0.0);
                }
            }));
            skillsSummary.forEach((s, aDouble) -> skillsSummary.put(s, aDouble / answerFeedbacks.size()));
        }));

        respond.setReviewStatus(ReviewStatus.ACCEPTED);
        return new ResponseEntity(skillsSummary, HttpStatus.OK);
    }

    @PostMapping("/api/v1/secured/vacancies/{vacancyId}/responds/{respondId}/review")
    public ResponseEntity<Respond> onReviewRespond(@PathVariable String respondId,
                                                   @PathVariable String vacancyId) {
        Respond respond = respondService.get(respondId).get();
        respond.setReviewStatus(ReviewStatus.ON_REVIEW);
        return new ResponseEntity<>(respondService.save(respond, vacancyId), HttpStatus.OK);
    }

    @PostMapping("/api/v1/secured/vacancies/{vacancyId}/responds/{respondId}/block")
    public ResponseEntity<Respond> blockRespond(@PathVariable String respondId,
                                                @PathVariable String vacancyId) {
        Respond respond = respondService.get(respondId).get();
        respond.setReviewStatus(ReviewStatus.BLOCKED);
        return new ResponseEntity<>(respondService.save(respond, vacancyId), HttpStatus.OK);
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
