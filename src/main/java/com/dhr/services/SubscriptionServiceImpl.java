package com.dhr.services;

import com.dhr.model.Subscription;
import com.dhr.repositories.SubscriptionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
public class SubscriptionServiceImpl implements SubscriptionService {
    @Autowired
    private SubscriptionRepository repository;

    @Override
    public Long save(Subscription Subscription) {
        repository.save(Subscription);
        return Subscription.getId();
    }

    @Override
    public void delete(Subscription Subscription) {
        repository.delete(Subscription);
    }

    @Override
    public Subscription update(Subscription Subscription) {
        repository.save(Subscription);
        return Subscription;
    }

    @Override
    public Optional<Subscription> get(Long id) {
        return repository.findById(id);
    }

    @Override
    public Iterable<Subscription> getAll() {
        return repository.findAll();
    }
}
