package com.dhr.services;

import com.dhr.model.Respond;
import com.dhr.repositories.QuestionRespondRepository;
import com.dhr.repositories.RespondRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class RespondServiceImpl implements RespondService {
    @Autowired
    private RespondRepository repository;

    @Autowired
    private QuestionRespondRepository questionRespondRepository;

    @Override
    public Respond save(Respond respond) {
        if (respond.getStartDate() == null) {
            respond.setStartDate(new Date());
        }
        if (respond.getId() == null) {
            respond.setId(Integer.toHexString(respond.hashCode()));
        }

//        if (respond.getRespondQuestions() != null && respond.getRespondQuestions().size() != 0) {
//            respond.getRespondQuestions().forEach(question -> {
//                if (question != null) {
//                    questionRespondRepository.save(question);
//                }
//            });
//        } else {
//            Optional<Respond> byId = repository.findById(respond.getId());
//            if (byId.isPresent() && byId.get().getRespondQuestions().size() != 0) {
//                respond.getRespondQuestions().addAll(repository.findById(respond.getId()).get().getRespondQuestions());
//            }
//        }
        repository.save(respond);
        return repository.findById(respond.getId()).get();
    }

    @Override
    public void delete(Respond respond) {
        respond.getRespondQuestions().forEach(q -> questionRespondRepository.delete(q));
        repository.delete(respond);
    }

    @Override
    public void update(Respond respond) {
        repository.save(respond);
    }

    @Override
    public Optional<Respond> get(String id) {
        return repository.findById(id);
    }

    @Override
    public Iterable<Respond> getAll() {
        return repository.findAll();
    }

    @Override
    public List<Respond> getAllByVacancyId(Long vacancyId) {
        return repository.findAllByVacancyId(vacancyId);
    }

    @Override
    public List<Respond> getByVacancyIdAndRespondId(Long vacancyId, String respondId) {
        return repository.findAllByVacancyIdAndId(vacancyId, respondId);
    }
}
