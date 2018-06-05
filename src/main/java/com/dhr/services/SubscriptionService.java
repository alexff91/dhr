package com.dhr.services;

import com.dhr.model.Subscription;

import java.util.Optional;

public interface SubscriptionService {
    Long save(Subscription subscription);

    void delete(Subscription subscription);

    Subscription update(Subscription subscription);

    Optional<Subscription> get(Long id);

    Iterable<Subscription> getAll();
}
