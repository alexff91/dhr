package com.dhr.services;

import com.dhr.model.QuestionAnswerFeedback;
import com.dhr.model.Respond;
import com.dhr.repositories.QuestionAnswerFeedbackRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@Transactional
public class QuestionAnswerFeedbackServiceIml implements QuestionAnswerFeedbackService {
    @Autowired
    private QuestionAnswerFeedbackRepository repository;

    @Autowired
    RespondService respondService;

    @Autowired
    private UserService userService;

    @Autowired
    private QuestionAnswerService questionAnswerService;

    @Override
    public Long save(Long questionAnswerId, String userId, QuestionAnswerFeedback feedback) {
        QuestionAnswerFeedback questionAnswerIdAndUserId = repository.findFirstByQuestionAnswerIdAndUserId(questionAnswerId, userId);
        if (questionAnswerIdAndUserId == null) {
            feedback.setUser(userService.get(userId).get());
            feedback.setQuestionAnswer(questionAnswerService.get(questionAnswerId).get());
            QuestionAnswerFeedback save = repository.save(feedback);
            return save.getId();
        } else {
            questionAnswerIdAndUserId.setComment(feedback.getComment());
            questionAnswerIdAndUserId.setDate(new Date());
            questionAnswerIdAndUserId.setSkillsFeedback(feedback.getSkillsFeedback());
            QuestionAnswerFeedback save = repository.save(questionAnswerIdAndUserId);
            return save.getId();
        }

    }

    @Override
    public void delete(QuestionAnswerFeedback feedback) {
        repository.delete(feedback);
    }

    @Override
    public QuestionAnswerFeedback update(QuestionAnswerFeedback feedback) {
        repository.save(feedback);
        return feedback;
    }

    @Override
    public Optional<QuestionAnswerFeedback> get(Long id) {
        return repository.findById(id);
    }

    @Override
    public Iterable<QuestionAnswerFeedback> getAll() {
        return repository.findAll();
    }

    @Override
    public Iterable<QuestionAnswerFeedback> getAllByQuestionAnswerId(Long id) {
        return repository.findAllByQuestionAnswerId(id);
    }

    @Override
    public QuestionAnswerFeedback findOneByQuestionAnswerId(Long questionAnswerId, String userId) {
        return repository.findFirstByQuestionAnswerIdAndUserId(questionAnswerId, userId);
    }

    public Map<String, Double> getSkillResponds(@PathVariable String respondId) {
        Respond respond = respondService.get(respondId).get();
        Map<String, Pair<Integer, Double>> skillsSummary = new HashMap<>();
        respond.getAnswers().forEach(questionAnswer -> {
            Iterable<QuestionAnswerFeedback> answerFeedbacksIterable = getAllByQuestionAnswerId(questionAnswer.getId());
            List<QuestionAnswerFeedback> answerFeedbacks = new LinkedList<>();
            answerFeedbacksIterable.forEach(answerFeedbacks::add);
            answerFeedbacks.forEach(feedback -> feedback.getSkillsFeedback().forEach((skillName, skillLevel) ->
            {
                if (skillsSummary.containsKey(skillName)) {
                    Pair<Integer, Double> count = skillsSummary.get(skillName);
                    skillsSummary.put(skillName,Pair.of(count.getFirst() + 1, count.getSecond() + skillLevel));

                } else {
                    skillsSummary.put(skillName,Pair.of(1, skillLevel + 0.0));
                }
            }));

        });
        Map<String, Double> skillsSummaryMap = new HashMap<>();
        skillsSummary.forEach((s, pair) -> skillsSummaryMap.put(s, pair.getSecond() / pair.getFirst()));
        return skillsSummaryMap;
    }


    @Autowired
    private EntityManager em;

    public List getCotisation() {
        Query query = em.createNativeQuery("select Annee,Mois,RetSonarwa from TCotisMIFOTRA2008 where matricule='10000493' order by Annee");
        List<Object[]> cotisation = query.getResultList();
        Object[] cotisationData;

        for (int i = 0; i < cotisation.size(); i++) {
            cotisationData = cotisation.get(i);

            System.out.print("Annee: " + cotisationData[0] + " Mois :" + cotisationData[1] + " Amount       :" + cotisationData[2] + "\n");

        }
        return query.getResultList();
    }
}
