package com.dhr.services;

import com.dhr.model.Respond;
import com.dhr.model.Vacancy;
import com.dhr.repositories.QuestionRespondRepository;
import com.dhr.repositories.RespondRepository;
import com.dhr.repositories.VacancyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class RespondServiceImpl implements RespondService {
    @Autowired
    private RespondRepository repository;

    @Autowired
    private QuestionRespondRepository questionRespondRepository;

    @Autowired
    private VacancyRepository vacancyRepository;

    @Override
    public Respond save(Respond respond, String vacancyId) {
        Respond respondByEmail = repository.findOneByEmailAndVacancyId(respond.getEmail(), vacancyId);
        if (respondByEmail != null) {
            return respondByEmail;
        }
        Vacancy vacancy = vacancyRepository.findById(vacancyId).get();
        respond.setVacancy(vacancy);
        if (respond.getStartDate() == null) {
            respond.setStartDate(new Date());
        }
        if (respond.getId() == null) {
            respond.setId(Integer.toHexString(respond.hashCode()) + Long.toHexString(new Date().getTime()));
        }
        vacancyRepository.save(vacancy);
        Respond savedRespond = repository.save(respond);
        vacancy.setRespondsCount(vacancy.getRespondsCount() + 1);
        vacancy.setUnansweredRespondsCount(vacancy.getUnansweredRespondsCount() + 1);
        vacancyRepository.save(vacancy);
        return savedRespond;
    }

    @Override
    public void delete(Respond respond) {
        respond.getAnswers().forEach(q -> questionRespondRepository.delete(q));
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
    public List<Respond> getAllByVacancyId(String vacancyId) {
        return repository.findAllByVacancyId(vacancyId);
    }

    @Override
    public Respond getByVacancyIdAndRespondId(String vacancyId, String respondId) {
        return repository.findOneByVacancyIdAndId(vacancyId, respondId);
    }
}
