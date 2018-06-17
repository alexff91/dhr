package com.dhr.services;

import com.dhr.model.Respond;
import com.dhr.model.RespondFeedback;
import com.dhr.model.User;
import com.dhr.model.Vacancy;
import com.dhr.model.enums.ReviewStatus;
import com.dhr.repositories.RespondFeedbackRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class RespondFeedbackServiceImpl implements RespondFeedbackService {
    @Autowired
    private RespondFeedbackRepository repository;

    @Autowired
    private RespondService respondService;

    @Autowired
    private UserService userService;

    @Autowired
    private VacancyService vacancyService;

    @Override
    public RespondFeedback save(String respondId, Long userId, RespondFeedback respondFeedback) {
        Respond respond = respondService.get(respondId).get();
        User user = userService.get(userId).get();
        respondFeedback.setUser(user);
        respondFeedback.setRespond(respond);
        if (respondFeedback.getDate() == null) {
            respondFeedback.setDate(new Date());
        }
        RespondFeedback feedback = repository.save(respondFeedback);
        Vacancy respondVacancy = respond.getVacancy();
        if (repository.findAllByRespondId(respondId).size() >= respondVacancy.getMinReviewCount()) {
            respond.setReviewStatus(ReviewStatus.REVIEWED);
            respondService.update(respond);
        }
        respondVacancy.setUnansweredRespondsCount(respondVacancy.getUnansweredRespondsCount() - 1);
        vacancyService.update(respond.getVacancy().getId(), respondVacancy);
        return feedback;
    }

    @Override
    public void delete(RespondFeedback respond) {
        repository.delete(respond);
    }

    @Override
    public void update(RespondFeedback respond) {
        repository.save(respond);
    }

    @Override
    public Optional<RespondFeedback> get(Long id) {
        return repository.findById(id);
    }

    @Override
    public Iterable<RespondFeedback> getAll() {
        return repository.findAll();
    }

    @Override
    public List<RespondFeedback> getAllByRespondId(String respondId) {
        return repository.findAllByRespondId(respondId);
    }
}
