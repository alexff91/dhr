package com.dhr.services;

import com.dhr.model.Interview;
import com.dhr.repositories.InterviewRepository;
import com.dhr.repositories.QuestionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Service
public class InterviewServiceImpl  implements InterviewService {
    @Autowired
    private InterviewRepository repository;

    @Autowired
    private QuestionRepository questionRepository;

    @Override
    public Long save(Interview interview) {
        repository.save(interview);
        interview.getQuestions().forEach(question -> questionRepository.save(question));
        return interview.getInterviewId();
    }

    @Override
    public void delete(Interview interview) {
        interview.getQuestions().forEach(q -> questionRepository.delete(q));
        repository.delete(interview);
    }

    @Override
    public void update(Interview interview) {
        repository.save(interview);
    }

    @Override
    public Optional<Interview> get(Long id) {
        return repository.findById(id);
    }

    @Override
    public List<Interview> getAll() {
        return repository.findAll();
    }
}
