package com.dhr.services;

import com.dhr.model.Respond;
import com.dhr.repositories.QuestionRespondRepository;
import com.dhr.repositories.RespondRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.nio.charset.Charset;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.Random;

@Service
public class RespondServiceImpl implements RespondService {
    @Autowired
    private RespondRepository repository;

    @Autowired
    private QuestionRespondRepository questionRespondRepository;

    @Override
    public String save(Respond respond) {
        respond.setStartDate(new Date());
        String generatedString = generateRandomString();
        respond.setRespondId(Integer.toHexString(respond.hashCode()) + generatedString);
        repository.save(respond);
        respond.getRespondQuestions().forEach(question -> {
            question.setRespondId(respond.getRespondId());
            questionRespondRepository.save(question);
        });
        return respond.getRespondId();
    }

    private String generateRandomString() {
        byte[] array = new byte[7];
        new Random().nextBytes(array);
        return new String(array, Charset.forName("UTF-8"));
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
    public List<Respond> getAll() {
        return repository.findAll();
    }

    @Override
    public List<Respond> getAllByVacancyId(Long vacancyId) {
        return repository.findAllByVacancyId(vacancyId);
    }

    @Override
    public List<Respond> getByVacancyIdAndRespondId(Long vacancyId, String respondId) {
        return repository.findFirstByVacancyIdAndRespondId(vacancyId, respondId);
    }
}
