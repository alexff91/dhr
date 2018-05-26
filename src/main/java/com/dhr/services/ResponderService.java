package com.dhr.services;

import com.dhr.model.Responder;

import java.util.List;
import java.util.Optional;

public interface ResponderService {
    Long save(Responder company);

    void delete(Responder company);

    Responder update(Responder company);

    Optional<Responder> get(Long id);

    List<Responder> getAll();
}
