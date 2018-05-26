package com.dhr.services;

import com.dhr.model.Responder;
import com.dhr.model.User;
import com.dhr.repositories.RespondersRepository;
import com.dhr.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ResponderServiceImpl implements ResponderService {
    @Autowired
    private RespondersRepository repository;

    @Override
    public Long save(Responder user) {
        repository.save(user);
        return user.getResponderId();
    }

    @Override
    public void delete(Responder user) {
        repository.delete(user);
    }

    @Override
    public Responder update(Responder user) {
        repository.save(user);
        return user;
    }

    @Override
    public Optional<Responder> get(Long id) {
        return repository.findById(id);
    }

    @Override
    public List<Responder> getAll() {
        return repository.findAll();
    }
}
