package com.dhr.services;

import com.dhr.model.Respond;
import com.dhr.repositories.QuestionRespondRepository;
import com.dhr.repositories.RespondRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RespondServiceImpl implements RespondService {
    @Autowired
    private RespondRepository repository;

    @Autowired
    private QuestionRespondRepository questionRespondRepository;

    @Override
    public Long save(Respond respond) {
        repository.save(respond);
        respond.getRespondQuestions().forEach(question -> {
            question.setRespondId(respond.getRespondId());
            questionRespondRepository.save(question);
        });
        return respond.getRespondId();
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
    public Optional<Respond> get(Long id) {
        return repository.findById(id);
    }

    @Override
    public List<Respond> getAll() {
        return repository.findAll();
    }

    @Override
    public List<Respond> getAllByVacancyId(Long vacancyId) {
        return repository.findAllByVacancyId(vacancyId);
    }

    @Override
    public List<Respond> getByVacancyIdAndRespondId(Long vacancyId, Long respondId) {
        return repository.findFirstByVacancyIdAndRespondId(vacancyId, respondId);
    }
}
